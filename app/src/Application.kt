package com.wsbinz

import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.*
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*

fun main() {
    embeddedServer(
            Tomcat,
            watchPaths = listOf("wsb_inz/app"),
            port = 8080,
            module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(StatusPages) {
        exception<Throwable> { e ->
            call.respondText(e.localizedMessage,
                    ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }
    install(ContentNegotiation) {
        gson{
        }
    }
    routing {
        post("/verify") {
            call.respond(Response(status = "OK"))
        }
    }
}

data class Response(val status: String)