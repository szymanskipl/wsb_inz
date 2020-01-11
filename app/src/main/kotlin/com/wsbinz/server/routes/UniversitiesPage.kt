package com.wsbinz.server.routes

import com.wsbinz.dao.*
import com.wsbinz.model.Course
import com.wsbinz.model.University
import com.wsbinz.server.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.request.receiveParameters
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import java.util.logging.Logger

fun Route.universitiesPage(dao: DAOFacade) {
    get<UniversitiesPage> {
        val session = call.sessions.get<AppSession>()
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            val universities: List<University> = dao.getAllUniversities()
            call.respond(
                FreeMarkerContent(
                    "universities.ftl",
                    mapOf("universities" to universities)
                )
            )
        }
    }
}

fun Route.newUniversityPage(dao: DAOFacade) {
    get<NewUniversityPage> {
        val session = call.sessions.get<AppSession>()
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            val courses: List<Course> = dao.getAllCourses()
            call.respond(
                FreeMarkerContent(
                    "newUniversity.ftl",
                    mapOf("courses" to courses)
                )
            )
        }
    }

    post<NewUniversityPage> {
        val post = call.receiveParameters()
        val newUniversityId = dao.createUniversity(post["name"].toString(), post["city"].toString(), post["urlAddress"].toString())
        val coursesId = post.getAll("courses_list[]")
        coursesId?.forEach {
            dao.createPair(newUniversityId, it.toInt())
        }
        call.respondRedirect(UniversitiesPage())
    }
}

fun Route.editUniversityPage(dao: DAOFacade) {
    get<EditUniversityPage> {
        val session = call.sessions.get<AppSession>()
        val university = dao.getUniversity(it.id)
        val allCourses = dao.getAllCourses()
        val selectedCourses = dao.getAllCoursesForUniversity(it.id)
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            if (university == null) {
                call.respondRedirect(UniversitiesPage())
            } else {
                call.respond(FreeMarkerContent("editUniversity.ftl", mapOf("university" to university, "courses" to allCourses, "selectedCourses" to selectedCourses)))
            }
        }
    }

    post<EditUniversityPage> {
        val post = call.receiveParameters()
        val universityId = it.id
        dao.updateUniversity(it.id, post["name"].toString(), post["city"].toString(), post["urlAddress"].toString())
        val courses = post.getAll("courses_list[]")
        dao.deleteAllPairs(it.id)
        courses?.forEach {
            dao.createPair(universityId, it.toInt())
        }
        call.respondRedirect(UniversitiesPage())
    }
}

fun Route.deleteUniversityPage(dao: DAOFacade) {
    get<DeleteUniversityPage> {
        val session = call.sessions.get<AppSession>()
        val university = dao.getUniversity(it.id)
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            if (university == null) {
                call.respondRedirect(UniversitiesPage())
            } else {
                dao.deleteUniversity(it.id)
                call.respondRedirect(UniversitiesPage())
            }
        }
    }
}
