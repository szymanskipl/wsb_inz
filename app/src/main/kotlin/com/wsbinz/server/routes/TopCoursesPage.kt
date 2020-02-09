package com.wsbinz.server.routes

import com.wsbinz.dao.DAOFacade
import com.wsbinz.model.University
import com.wsbinz.server.CitiesPage
import com.wsbinz.server.TopCourses
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route

fun Route.topCourses(dao: DAOFacade) {
    get<TopCourses> {
        val courses = dao.getAllCourses().sortedBy { it.name }
        call.respond(
            FreeMarkerContent(
                "topCourses.ftl",
                mapOf(
                    "courses" to courses
                )
            )
        )
    }
}