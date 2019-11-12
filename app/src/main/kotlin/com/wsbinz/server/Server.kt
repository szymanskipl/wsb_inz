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

@Location("/admin/login")
data class Login(val error: String? = null)

@Location("/admin/universities")
class UniversitiesPage()

@Location("/admin/logout")
class Logout()

val dao = DAOFacadeDatabase(
    Database.connect(
        "jdbc:postgresql://localhost:5432/wsb?user=student",
        driver = "org.postgresql.Driver"
    )
)

val hashKey = hex("12684616132154684")

data class AppSession(val userEmail: String)

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
    install(Sessions) {
        cookie<com.wsbinz.server.AppSession>("SESSION"){
            transform(SessionTransportTransformerMessageAuthentication(hashKey))
        }
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    routing{
        login(dao)
        universitiesPage(dao)
    }
}

suspend fun ApplicationCall.respondRedirect(location: Any) = respondRedirect(url(location), permanent = false)

private val emailPattern = "(.*@.*\\..+)".toRegex()
internal fun emailValidation(email: String) = email.matches(emailPattern)

val serverLogger: Logger = LoggerFactory.getLogger("Server")
