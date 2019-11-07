package com.wsbinz

import com.wsbinz.dao.DAOFacade
import com.wsbinz.dao.DAOFacadeDatabase
import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.*
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import io.ktor.locations.Locations
import org.jetbrains.exposed.sql.Database
import io.ktor.auth.*
import io.ktor.freemarker.*
import freemarker.cache.*
import io.ktor.features.CallLogging

val dao = DAOFacadeDatabase(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
fun main(args: Array<String>) {
    dao.init()
    embeddedServer(Netty, watchPaths = listOf("wsb_inz/app"), port = 8080){}.start(true)

}

fun Application.mainWithDependencies(dao: DAOFacade) {

    install(StatusPages) {
        exception<Throwable> { e ->
            call.respondText(
                e.localizedMessage,
                ContentType.Text.Plain, HttpStatusCode.InternalServerError
            )
        }
    }
    install(ContentNegotiation) {
        gson {
        }
    }
    install(Locations)
    install(CallLogging)
    install(Authentication) {
        form("login") {
            userParamName = "name"
            passwordParamName = "password"
            challenge = FormAuthChallenge.Redirect{ "/login"}
            validate { credentials ->
                if (credentials.name == credentials.password)
                    UserIdPrincipal(credentials.name)
                else null
            }
        }
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    routing {
        route("/login") {
            get() {
                call.respond(FreeMarkerContent("login.ftl", null))
            }
            authenticate("login") {
                post() {
                    val principal = call.principal<UserIdPrincipal>()
                    call.respondText { "OK" }
                }
            }
        }
    }
}

fun hash(password: String): String {
    val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
    return passwordEncoder.encode(password)
}

data class Response(val status: String)