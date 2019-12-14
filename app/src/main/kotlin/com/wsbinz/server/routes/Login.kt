package com.wsbinz.server.routes

import com.wsbinz.dao.*
import com.wsbinz.server.*
import io.ktor.application.*
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.locations.*

fun Route.login(dao: DAOFacade) {
    get<Login> {
            call.respond(
                FreeMarkerContent(
                    "login.ftl", mapOf("error" to it.error)
                    )
            )
    }

    authenticate("auth") {
        post<Login> {
            val principal = call.principal<UserIdPrincipal>()
            if (principal != null) {
                call.sessions.set(AppSession(principal.name))
                call.respondRedirect(CoursesPage())
            }
        }
    }

    get<Logout> {
        call.sessions.clear<AppSession>()
        call.respondRedirect(Login())
    }

}



