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
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.*
import org.postgresql.*
import io.ktor.sessions.*
import io.ktor.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

@Location("/admin/login")
data class Login(val error: String? = null)

@Location("/admin/courses")
class CoursesPage()

@Location("/admin/logout")
class Logout()

val dao = DAOFacadeDatabase(
    Database.connect(
        "jdbc:postgresql://localhost:5432/wsb?user=student",
        driver = "org.postgresql.Driver"
    )
)

data class AppSession(val visitor: String)


fun startServer() = embeddedServer(Netty, port = 8080) {
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
        cookie<com.wsbinz.server.AppSession>("SESSION") {
            val secret = "w298weuefj9348hferh87t30fodf384u3948hert"
            transform(SessionTransportTransformerMessageAuthentication(secret.toByteArray(), "HmacSHA256"))
            cookie.duration = Duration.ofMinutes(10)
        }
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    routing {
        login(dao)
        coursesPage(dao)
        static("/static") {
            resources("static")
        }
    }
}

suspend fun ApplicationCall.respondRedirect(location: Any) = respondRedirect(url(location), permanent = false)

private val emailPattern = "(.*@.*\\..+)".toRegex()
internal fun emailValidation(email: String) = email.matches(emailPattern)

val serverLogger: Logger = LoggerFactory.getLogger("Server")
