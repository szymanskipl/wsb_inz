package com.wsbinz.dao

import com.wsbinz.hash
import com.wsbinz.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable

interface DAOFacade: Closeable {
    fun init()
    fun createUser(email: String, passwordHash: String, role: String)
    fun getUser(id: Int): User?
}

class DAOFacadeDatabase(val db: Database): DAOFacade{

    override fun init() = transaction(db) {
        SchemaUtils.create(Users)
        val users = listOf(
            User(
                "szymanski@stethome.com",
                hash("testowe"),
                "admin"))
        Users.batchInsert(users){ user ->
            this[Users.email] = user.email
            this[Users.passwordHash] = user.passwordHash
            this[Users.role] = user.role
        }
        Unit
    }

    override fun createUser(email: String, passwordHash: String, role: String) = transaction(db) {
        Users.insert {
            it[Users.email] = email
            it[Users.passwordHash] = passwordHash
            it[Users.role] = role
        }
        Unit
    }

    override fun getUser(id: Int) = transaction(db) {
        Users.select {Users.id eq id}.map {
            User(it[Users.email], it[Users.passwordHash], it[Users.role])
        }.singleOrNull()
    }

    override fun close() {}

}