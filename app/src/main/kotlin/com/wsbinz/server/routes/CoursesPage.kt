package com.wsbinz.server.routes

import com.wsbinz.dao.*
import com.wsbinz.model.Category
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
            val courses: List<Course> = dao.getAllCourses().sortedBy { it.name }
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
            val categories: List<Category>? = dao.getAllCategories()
            call.respond(
                FreeMarkerContent(
                    "newCourse.ftl", mapOf("categories" to categories)
                )
            )
        }
    }

    post<NewCoursePage> {
        val post = call.receiveParameters()
        dao.createCourse(post["name"].toString(), post["description"].toString(), post["category"]!!.toInt())
        call.respondRedirect(CoursesPage())
    }
}

fun Route.editCoursePage(dao: DAOFacade) {
    get<EditCoursePage> {
        val session = call.sessions.get<AppSession>()
        val course = dao.getCourse(it.id)
        val categories = dao.getAllCategories()
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            if (course == null) {
                call.respondRedirect(CoursesPage())
            } else {
                call.respond(
                    FreeMarkerContent(
                        template = "editCourse.ftl",
                        model = mapOf("course" to course, "categories" to categories)
                    ))
            }
        }
    }

    post<EditCoursePage> {
        val post = call.receiveParameters()
        dao.updateCourse(it.id, post["name"].toString(), post["description"].toString(), post["category"]!!.toInt())
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
                dao.deleteAllPairsUniversityCourse(courseId = it.id)
                dao.deleteCourse(it.id)
                call.respondRedirect(CoursesPage())
            }
        }
    }
}
