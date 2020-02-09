package com.wsbinz.server.routes

import com.wsbinz.dao.*
import com.wsbinz.model.Answer
import com.wsbinz.model.Category
import com.wsbinz.model.Question
import com.wsbinz.server.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.request.receiveParameters

var questionNumber: Int = 0
var surveyId: Int? = null

fun Route.surveyPage(dao: DAOFacade) {

    get<SurveyPage> {
        if (surveyId == null) surveyId = dao.newSurvey()
        val allQuestions: List<Question> = dao.getAllQuestions()
        val question = allQuestions[questionNumber]
        val answers: List<Answer>? = dao.getAllAnswersForQuestion(question.id)
        call.respond(
            FreeMarkerContent(
                "survey.ftl",
                mapOf(
                    "question" to question,
                    "answers" to answers,
                    "surveyId" to surveyId
                )
            )
        )
    }


    post<SurveyPage> {
        questionNumber++
        val post = call.receiveParameters()
        dao.putAnswerToSurvey(
            surveyId = post["surveyId"]!!.toInt(),
            answerId = post["answer_id"]!!.toInt()
        )
        if (questionNumber < 11) {
            call.respondRedirect(SurveyPage())
        } else {
            questionNumber = 0
            call.respondRedirect(SurveyResultPage(post["surveyId"]!!.toInt()))
        }
    }
}

fun Route.surveyResultPage(dao: DAOFacade) {
    get<SurveyResultPage> {
        val categories = dao.getAllCategories()
        var results = mutableMapOf<Category, Int>()
        categories
            ?.map { category ->
                results?.put(category, 0)
            }
        val answers = dao.getAnswersForSurvey(surveyId = it.id)
        answers
            ?.map { answer ->
                val categories = answer.categories
                categories
                    ?.map { category ->
                        results?.merge(category, 1, Int::plus)
                    }
            }

        val recommendedCourses = dao.getCoursesForCategory(
            results?.maxBy { result -> result.value }!!.key.id
        )?.map { course ->
            dao.setRecommendedCourses(surveyId = it.id, courseId = course.id)
        }


        val courses = dao.getCoursesForSurvey(it.id)

        call.respond(
            FreeMarkerContent(
                "surveyResult.ftl",
                mapOf(
                    "courses" to courses
                )
            )
        )

    }
}