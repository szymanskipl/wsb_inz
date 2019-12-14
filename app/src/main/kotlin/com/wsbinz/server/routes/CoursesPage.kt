package com.wsbinz.server.routes

import com.wsbinz.dao.*
import com.wsbinz.model.Course
import com.wsbinz.server.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.sessions.get
import io.ktor.sessions.sessions

fun Route.coursesPage(dao: DAOFacade) {
    get<CoursesPage> {
        val session = call.sessions.get<AppSession>()
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            serverLogger.info(call.sessions.get<AppSession>()!!.visitor)
            val courses: List<Course> = dao.getAllCourses()
            call.respond(
                FreeMarkerContent(
                    "courses.ftl",
                    mapOf("courses" to courses)
                )
            )
        }
    }
}

