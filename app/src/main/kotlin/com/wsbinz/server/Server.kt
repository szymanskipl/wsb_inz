package com.wsbinz.server

import com.wsbinz.auth.BcryptHasher
import com.wsbinz.dao.*
import com.wsbinz.model.*
import com.wsbinz.server.routes.*
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.freemarker.FreeMarker
import io.ktor.gson.gson
import io.ktor.http.*
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import org.jetbrains.exposed.sql.*
import org.postgresql.*
import io.ktor.sessions.*
import io.ktor.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.time.Duration

@Location("/admin/login")
data class Login(val error: String? = null)

@Location("/admin/kierunki")
class CoursesPage()

@Location("/admin/uczelnie")
class UniversitiesPage()

@Location("/admin/wyloguj")
class Logout()

@Location("/admin/kierunki/nowy")
class NewCoursePage()

@Location("/admin/kierunki/{id}/edycja")
class EditCoursePage(val id: Int)

@Location("/admin/kierunki/{id}/usun")
class DeleteCoursePage(val id: Int)

@Location("/admin/uczelnie/nowa")
class NewUniversityPage()

@Location("/admin/uczelnie/{id}/edycja")
class EditUniversityPage(val id: Int)

@Location("/admin/uczelnie/{id}/usun")
class DeleteUniversityPage(val id: Int)

@Location("/admin/pytania")
class QuestionsPage()

@Location("/admin/pytania/nowe")
class NewQuestionPage()

@Location("/admin/pytania/{id}/edycja")
class EditQuestionPage(val id: Int)

@Location("/admin/pytania/{id}/usun")
class DeleteQuestionPage(val id: Int)

@Location("/ankieta")
class SurveyPage()

@Location("/wynik_ankiety")
class SurveyResultPage(val id: Int)

@Location("/miasta")
class CitiesPage()

@Location("/kierunki")
class TopCourses()

val dao = DAOFacadeDatabase(
    Database.connect(
        "jdbc:postgresql://localhost:5432/wsb?user=student",
        driver = "org.postgresql.Driver"
    )
)

data class AppSession(val visitor: String)


fun startServer() = embeddedServer(Jetty, port = 8080) {
    dao.init()
    module(dao)
}.start(true)

fun Application.module(dao: DAOFacade) {
    install(StatusPages) {
    }
    install(ContentNegotiation) {
        gson {
        }
    }
    install(CallLogging)
    install(DefaultHeaders)
    install(ConditionalHeaders)
    install(PartialContent)
    install(Locations)
    install(Authentication) {
        form(name = "auth") {
            userParamName = "username"
            passwordParamName = "password"
            challenge {
                val errors: Map<Any, AuthenticationFailedCause> = call.authentication.errors
                val error = Login()
                when (errors.values.singleOrNull()) {
                    AuthenticationFailedCause.InvalidCredentials ->
                        call.respondRedirect(error.copy(error = "true"))

                    AuthenticationFailedCause.NoCredentials ->
                        call.respondRedirect(error.copy(error = "true"))

                    else ->
                        call.respondRedirect(Login())
                }
            }
            validate { credentials ->
                if (dao.userVerify(credentials.name, credentials.password) != null) {
                    serverLogger.info("correct credentials")
                    UserIdPrincipal(credentials.name)
                } else null
            }
        }
    }
    install(Sessions) {
        val encryptKey = hex("b8102a69cb71141edf8df21d4acf83b2")
        val authKey = hex("fe7c4a2c78ad27b12c197219e647865b")
        cookie<AppSession>(
            "SESSION",
            directorySessionStorage(File(".session"), cached = true)
        ) {
            cookie.path = "/"
            transform(SessionTransportTransformerEncrypt(encryptKey, authKey))
        }
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    routing {
        static("/static") {
            resources("static")
        }
        get("/") {
            call.respondRedirect(CitiesPage())
        }
        login(dao)
        coursesPage(dao)
        newCoursePage(dao)
        editCoursePage(dao)
        deleteCoursePage(dao)
        universitiesPage(dao)
        newUniversityPage(dao)
        editUniversityPage(dao)
        deleteUniversityPage(dao)
        questionsPage(dao)
        newQuestionPage(dao)
        editQuestionPage(dao)
        deleteQuestionPage(dao)
        surveyPage(dao)
        surveyResultPage(dao)
        citiesPage(dao)
        topCourses(dao)
    }
}

suspend fun ApplicationCall.respondRedirect(location: Any) = respondRedirect(url(location), permanent = false)

private val emailPattern = "(.*@.*\\..+)".toRegex()
internal fun emailValidation(email: String) = email.matches(emailPattern)

val serverLogger: Logger = LoggerFactory.getLogger("Server")
