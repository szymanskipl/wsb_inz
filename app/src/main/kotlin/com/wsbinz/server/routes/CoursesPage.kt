package com.wsbinz.server.routes

import com.wsbinz.dao.*
import com.wsbinz.model.Course
import com.wsbinz.server.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.request.receiveParameters
import io.ktor.sessions.get
import io.ktor.sessions.sessions

fun Route.coursesPage(dao: DAOFacade) {
    get<CoursesPage> {
        val session = call.sessions.get<AppSession>()
        if (session == null) {
            call.respondRedirect(Login())
        } else {
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

fun Route.newCoursePage(dao: DAOFacade) {
    get<NewCoursePage> {
        val session = call.sessions.get<AppSession>()
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            call.respond(
                FreeMarkerContent(
                    "newCourse.ftl", null
                )
            )
        }
    }

    post<NewCoursePage> {
        val post = call.receiveParameters()
        dao.createCourse(post["name"].toString(), post["description"].toString(), post["category"].toString())
        call.respondRedirect(CoursesPage())
    }
}

fun Route.editCoursePage(dao: DAOFacade) {
    get<EditCoursePage> {
        val session = call.sessions.get<AppSession>()
        val course = dao.getCourse(it.id)
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            if (course == null) {
                call.respondRedirect(CoursesPage())
            } else {
                call.respond(FreeMarkerContent("editCourse.ftl", mapOf("course" to course)))
            }
        }
    }

    post<EditCoursePage> {
        val post = call.receiveParameters()
        dao.updateCourse(it.id, post["name"].toString(), post["description"].toString(), post["category"].toString())
        call.respondRedirect(CoursesPage())
    }
}

fun Route.deleteCoursePage(dao: DAOFacade) {
    get<DeleteCoursePage> {
        val session = call.sessions.get<AppSession>()
        val course = dao.getCourse(it.id)
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            if (course == null) {
                call.respondRedirect(CoursesPage())
            } else {
                dao.deleteCourse(it.id)
                call.respondRedirect(CoursesPage())
            }
        }
    }
}
