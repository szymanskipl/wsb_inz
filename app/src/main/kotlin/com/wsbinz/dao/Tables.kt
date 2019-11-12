package com.wsbinz.dao

import org.jetbrains.exposed.sql.Table

val tables = arrayOf(
    Users,
    Universities
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