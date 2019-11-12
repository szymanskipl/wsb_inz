package com.wsbinz.server.routes

import com.wsbinz.dao.*
import com.wsbinz.server.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.locations.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Route.login(dao: DAOFacade) {
    get<Login> {
        val user = call.sessions.get<AppSession>()
        if (user != null) {
            call.respondRedirect(UniversitiesPage())
        } else {
            call.respond(
                FreeMarkerContent(
                    "login.ftl", mapOf("error" to it.error)
                )
            )
        }
    }

    post<Login> {
        val post = call.receive<Parameters>()
        val userEmail = post["userEmail"]
        val password = post["password"]

        val error = Login()

        val login = when {
            userEmail!!.length < 4 -> null
            password!!.length < 8 -> null
            !emailValidation(userEmail) -> null
            else -> dao.userVerify(userEmail, password)
        }
        if (login == null) {
            call.respondRedirect(error.copy(error = "Niepoprawny email lub hasło"))
        } else {
            call.sessions.set(AppSession(login.email))
            call.respondRedirect(UniversitiesPage())
        }
    }

    get<Logout> {
        call.sessions.clear<AppSession>()
        call.respondRedirect(Login())
    }

}



