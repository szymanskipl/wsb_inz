package com.wsbinz.dao

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

val tables = arrayOf(
    Users,
    Universities,
    Courses,
    UniversityCourse,
    Questions,
    Answers
)


object Users : Table("users") {
    val id = integer("id").primaryKey().autoIncrement()
    val email = varchar("email", 100).uniqueIndex()
    val passwordHash = varchar("password", 64)
    val role = varchar("role", 20)
}

object Universities : Table("universities") {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val city = varchar("city", 20)
    val urlAddress = varchar("url", 100)
}

object Courses : Table("courses") {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val category = varchar("category", 50)
}

object UniversityCourse : Table("university_courses") {
    val universityId = integer("university_id")
    val courseId = integer("course_id")
}

object Questions : Table("questions") {
    val id = integer("id").primaryKey().autoIncrement()
    val text = varchar("text", 255)
}

object Answers : Table("answers") {
    val questionId = integer("question_id")
    val text = varchar("text", 255)
}