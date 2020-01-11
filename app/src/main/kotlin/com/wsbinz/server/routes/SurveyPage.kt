package com.wsbinz.server.routes

import com.wsbinz.dao.*
import com.wsbinz.model.Answer
import com.wsbinz.model.Course
import com.wsbinz.model.Question
import com.wsbinz.server.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.request.receiveParameters
import io.ktor.sessions.get
import io.ktor.sessions.sessions

fun Route.surveyPage(dao: DAOFacade) {
    get<SurveyPage> {
        val session = call.sessions.get<AppSession>()
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            val allQuestions: List<Question> = dao.getAllQuestions()
            var i = 0
            val question = allQuestions[i]
            val answers: List<Answer>? = dao.getAllAnswersForQuestion(question.id)
            call.respond(
                FreeMarkerContent(
                    "survey.ftl",
                    mapOf("question" to question, "answers" to answers)
                )
            )
        }
    }
}