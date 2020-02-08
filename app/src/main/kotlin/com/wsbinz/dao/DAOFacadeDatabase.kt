package com.wsbinz.dao

import com.wsbinz.auth.BcryptHasher
import com.wsbinz.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.io.Closeable

interface DAOFacade : Closeable {
    //init database
    fun init()

    //Users
    fun createUser(email: String, passwordHash: String, role: String)

    fun getUser(email: String): User?
    fun userVerify(email: String, password: String): User?
    //Universities
    fun createUniversity(name: String, city: String, urlAddress: String): Int

    fun updateUniversity(id: Int, name: String, city: String, urlAddress: String)
    fun deleteUniversity(id: Int)
    fun getUniversity(id: Int): University?
    fun getAllUniversities(): List<University>
    //Courses
    fun createCourse(name: String, description: String, categoryId: Int)

    fun updateCourse(id: Int, name: String, description: String, categoryId: Int)
    fun deleteCourse(id: Int)
    fun getCourse(id: Int): Course?
    fun getAllCourses(): List<Course>
    //UniversityCourse
    fun createPair(universityId: Int, course_id: Int)

    fun getAllCoursesForUniversity(universityId: Int): List<Course>?
    fun deleteAllPairs(universityId: Int)
    //Categories
    fun getCategory(id: Int): Category?

    fun getAllCategories(): List<Category>?
    fun getAllCategoriesForAnswer(answerId: Int): List<Category>?
    //Questions
    fun createQuestion(text: String): Int

    fun getQuestion(id: Int): Question?
    fun getAllQuestions(): List<Question>
    fun deleteQuestion(id: Int)
    fun updateQuestion(id: Int, text: String)
    //Answers
    fun createAnswer(questionId: Int, text: String): Int

    fun getAllAnswersForQuestion(questionId: Int): List<Answer>?
    fun getAllAnswers(): List<Answer>?
    fun deleteAllAnswers(questionId: Int)
    //AnswerCategory
    fun createPairAnswerCategory(answerId: Int, categoryId: Int)

    fun deletePairsAnswerCategory(answerId: Int)
}

class DAOFacadeDatabase(val db: Database) : DAOFacade {

    //init database
    override fun init() = transaction(db) {
        SchemaUtils.create(*tables)
        createUser("admin@admin.pl", BcryptHasher.hashPassword("admin123"), "admin")
        Unit
    }

    //Users
    override fun createUser(email: String, passwordHash: String, role: String) = transaction(db) {
        if (getUser(email) == null) {
            Users.insert {
                it[Users.email] = email
                it[Users.passwordHash] = passwordHash
                it[Users.role] = role
            }
        }
        Unit
    }

    override fun getUser(email: String) = transaction(db) {
        Users.select { Users.email eq email }.map {
            User(it[Users.id], it[Users.email], it[Users.passwordHash], it[Users.role])
        }.singleOrNull()
    }

    override fun userVerify(email: String, password: String) = transaction(db) {
        Users.select { Users.email eq email }.mapNotNull {
            if (BCrypt.checkpw(password, it[Users.passwordHash])) {
                User(it[Users.id], it[Users.email], it[Users.passwordHash], it[Users.role])
            } else {
                null
            }
        }.singleOrNull()
    }

    //Universities
    override fun createUniversity(name: String, city: String, urlAddress: String): Int = transaction(db) {
        Universities.insert {
            it[Universities.name] = name
            it[Universities.city] = city
            it[Universities.urlAddress] = urlAddress
        } get Universities.id
    }

    override fun updateUniversity(id: Int, name: String, city: String, urlAddress: String) = transaction(db) {
        Universities.update({ Universities.id eq id }) {
            it[Universities.name] = name
            it[Universities.city] = city
            it[Universities.urlAddress] = urlAddress
        }
        Unit
    }

    override fun deleteUniversity(id: Int) = transaction(db) {
        Universities.deleteWhere { Universities.id eq id }
        Unit
    }

    override fun getUniversity(id: Int) = transaction(db) {
        Universities.select { Universities.id eq id }.map {
            University(it[Universities.id], it[Universities.name], it[Universities.city], it[Universities.urlAddress])
        }.singleOrNull()
    }

    override fun getAllUniversities() = transaction(db) {
        Universities.selectAll().map {
            University(it[Universities.id], it[Universities.name], it[Universities.city], it[Universities.urlAddress])
        }
    }

    //Courses
    override fun createCourse(name: String, description: String, categoryId: Int) = transaction(db) {
        Courses.insert {
            it[Courses.name] = name
            it[Courses.description] = description
            it[Courses.categoryId] = categoryId
        }
        Unit
    }

    override fun updateCourse(id: Int, name: String, description: String, categoryId: Int) = transaction(db) {
        Courses.update({ Courses.id eq id }) {
            it[Courses.name] = name
            it[Courses.description] = description
            it[Courses.categoryId] = categoryId
        }
        Unit
    }

    override fun deleteCourse(id: Int) = transaction(db) {
        Courses.deleteWhere { Courses.id eq id }
        Unit
    }

    override fun getCourse(id: Int) = transaction(db) {
        Courses.select { Courses.id eq id }.map {
            Course(it[Courses.id], it[Courses.name], it[Courses.description], it[Courses.categoryId])
        }.singleOrNull()
    }

    override fun getAllCourses() = transaction(db) {
        Courses.selectAll().map {
            Course(it[Courses.id], it[Courses.name], it[Courses.description], it[Courses.categoryId])
        }
    }

    //UniversityCourse
    override fun createPair(universityId: Int, course_id: Int) = transaction(db) {
        UniversityCourse.insert {
            it[UniversityCourse.universityId] = universityId
            it[UniversityCourse.courseId] = course_id
        }
        Unit
    }

    override fun getAllCoursesForUniversity(universityId: Int) = transaction(db) {
        UniversityCourse
            .leftJoin(Courses, { UniversityCourse.courseId }, { Courses.id })
            .select { UniversityCourse.universityId eq universityId }
            .map {
                Course(it[Courses.id], it[Courses.name], it[Courses.description], it[Courses.categoryId])
            }
    }

    override fun deleteAllPairs(universityId: Int) = transaction(db) {
        UniversityCourse
            .deleteWhere {
                UniversityCourse.universityId eq universityId
            }
        Unit
    }

    //Categories
    override fun getCategory(id: Int): Category? = transaction(db) {
        Categories
            .select {
                Categories.id eq id
            }
            .map {
                Category(it[Categories.id], it[Categories.name])
            }
            .singleOrNull()
    }

    override fun getAllCategories(): List<Category>? = transaction(db) {
        Categories
            .selectAll()
            .map {
                Category(it[Categories.id], it[Categories.name])
            }
            .sortedBy { it.id }
    }

    override fun getAllCategoriesForAnswer(answerId: Int): List<Category>? = transaction(db) {
        AnswerCategory
            .innerJoin(Answers, { AnswerCategory.answerId }, { Answers.id })
            .innerJoin(Categories, { AnswerCategory.categoryId }, { Categories.id })
            .select { Answers.id eq answerId }
            .map {
                Category(it[Categories.id], it[Categories.name])
            }
    }

    //Questions
    override fun createQuestion(text: String): Int = transaction(db) {
        Questions
            .insert {
                it[Questions.text] = text
            } get Questions.id
    }

    override fun getAllQuestions(): List<Question> = transaction(db) {
        Questions
            .selectAll()
            .map {
                Question(it[Questions.id], it[Questions.text])
            }
            .sortedBy { it.id }
    }

    override fun deleteQuestion(id: Int) = transaction(db) {
        Questions
            .deleteWhere {
                Questions.id eq id
            }
        Unit
    }

    override fun getQuestion(id: Int): Question? = transaction(db) {
        Questions
            .select { Questions.id eq id }
            .map {
                Question(it[Questions.id], it[Questions.text])
            }
            .singleOrNull()
    }

    override fun updateQuestion(id: Int, text: String) = transaction(db) {
        Questions
            .update({ Questions.id eq id }) {
                it[Questions.text] = text
            }
        Unit
    }

    //Answers
    override fun createAnswer(questionId: Int, text: String): Int = transaction(db) {
        Answers
            .insert {
                it[Answers.questionId] = questionId
                it[Answers.text] = text
            } get Answers.id
    }

    override fun getAllAnswersForQuestion(questionId: Int): List<Answer> = transaction(db) {
        Answers
            .select {
                Answers.questionId eq questionId
            }
            .map {
                Answer(it[Answers.id], it[Answers.questionId], it[Answers.text])
            }
    }

    override fun getAllAnswers() = transaction(db) {
        Answers
            .selectAll()
            .map {
                Answer(it[Answers.id], it[Answers.questionId], it[Answers.text])
            }
    }

    override fun deleteAllAnswers(questionId: Int) = transaction(db) {
        Answers
            .deleteWhere {
                Answers.questionId eq questionId
            }
        Unit
    }

    //AnswerCategory
    override fun createPairAnswerCategory(answerId: Int, categoryId: Int) = transaction(db) {
        AnswerCategory
            .insert {
                it[AnswerCategory.answerId] = answerId
                it[AnswerCategory.categoryId] = categoryId
            }
        Unit
    }


    override fun deletePairsAnswerCategory(answerId: Int) = transaction(db) {
        AnswerCategory
            .deleteWhere {
                AnswerCategory.answerId eq answerId
            }
        Unit
    }

    override fun close() {}

}
