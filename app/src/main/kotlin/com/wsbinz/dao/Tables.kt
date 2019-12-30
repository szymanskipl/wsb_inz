package com.wsbinz.dao

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

val tables = arrayOf(
    Users,
    Universities,
    Courses
)


object Users : Table("users") {
    val id = integer("id").primaryKey().autoIncrement()
    val email = varchar("email", 100).uniqueIndex()
    val passwordHash = varchar("password", 64)
    val role = varchar("role", 20)
}

object Universities : Table("universities") {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255).uniqueIndex()
    val city = varchar("city", 20)
    val urlAddress = varchar("url", 100)
}

object Courses : Table("courses") {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255).uniqueIndex()
    val description = varchar("description", 255)
    val category = varchar("category", 50)
}