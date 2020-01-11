package com.wsbinz.server.routes

import com.wsbinz.dao.*
import com.wsbinz.model.Answer
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

fun Route.questionsPage(dao: DAOFacade) {
    get<QuestionsPage> {
        val session = call.sessions.get<AppSession>()
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            val questions: List<Question> = dao.getAllQuestions()
            val answers: List<Answer>? = dao.getAllAnswers()
            call.respond(
                FreeMarkerContent(
                    "questions.ftl",
                    mapOf("questions" to questions, "answers" to answers)
                )
            )
        }
    }
}

fun Route.newQuestionPage(dao: DAOFacade) {
    get<NewQuestionPage> {
        val session = call.sessions.get<AppSession>()
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            call.respond(
                FreeMarkerContent(
                    "newQuestion.ftl",
                    null
                )
            )
        }
    }

    post<NewQuestionPage> {
        val post = call.receiveParameters()
        val newQuestionId = dao.createQuestion(post["text"].toString())
        val answers = post.getAll("answers[]")
        answers?.forEach {
            if (it.isNotEmpty()) {
                dao.createAnswer(newQuestionId, it)
            }
        }
        call.respondRedirect(QuestionsPage())
    }
}

fun Route.editQuestionPage(dao: DAOFacade) {
    get<EditQuestionPage> {
        val session = call.sessions.get<AppSession>()
        val question = dao.getQuestion(it.id)
        val answers = dao.getAllAnswersForQuestion(it.id)
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            if (question == null) {
                call.respondRedirect(QuestionsPage())
            } else {
                call.respond(
                    FreeMarkerContent(
                        "editQuestion.ftl",
                        mapOf("question" to question, "answers" to answers)
                    )
                )
            }
        }
    }

    post<EditQuestionPage> {
        val post = call.receiveParameters()
        val questionId = it.id
        dao.updateQuestion(it.id, post["text"].toString())
        val answers = post.getAll("answers[]")
        dao.deleteAllAnswers(questionId)
        answers?.forEach {
            if (it.isNotEmpty()) {
                dao.createAnswer(questionId, it)
            }
        }
        call.respondRedirect(QuestionsPage())
    }
}

fun Route.deleteQuestionPage(dao: DAOFacade) {
    get<DeleteQuestionPage> {
        val session = call.sessions.get<AppSession>()
        val question = dao.getQuestion(it.id)
        if (session == null) {
            call.respondRedirect(Login())
        } else {
            if (question == null) {
                call.respondRedirect(QuestionsPage())
            } else {
                dao.deleteQuestion(it.id)
                dao.deleteAllAnswers(it.id)
                call.respondRedirect(QuestionsPage())
            }
        }
    }
}
