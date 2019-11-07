package com.wsbinz.dao

import org.jetbrains.exposed.sql.Table

object Users: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val email = varchar("email", 100)
    val passwordHash = varchar("password", 64)
    val role = varchar("role", 20)
}