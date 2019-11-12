package com.wsbinz.server.routes

import com.wsbinz.dao.*
import com.wsbinz.server.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.sessions.get
import io.ktor.sessions.sessions

fun Route.universitiesPage(dao: DAOFacade) {
    get<UniversitiesPage> {
        val user = call.sessions.get<AppSession>()
        if (user == null) {
            call.respondRedirect(Login())
        } else {
            call.respond(FreeMarkerContent("adminPanel.ftl", null))
        }
    }
}

