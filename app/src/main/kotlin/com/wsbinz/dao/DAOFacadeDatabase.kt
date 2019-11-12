package com.wsbinz.dao

import com.wsbinz.auth.BcryptHasher
import com.wsbinz.dao.*
import com.wsbinz.model.University
import com.wsbinz.model.User
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
    //Universities
    fun createUniversity(name: String, city: String, urlAddress: String)
    fun updateUniversity(id: Int, name: String, city: String, urlAddress: String)
    fun deleteUniversity(id: Int)
    fun getUniversity(id: Int): University?
    fun getAllUniversities(): List<University>
    fun userVerify(email: String, password: String): User?
}

class DAOFacadeDatabase(val db: Database) : DAOFacade {

    //init database
    override fun init() = transaction(db) {
        SchemaUtils.create(*tables)
//        val users = listOf(
//            User(
//                "szymanski@stethome.com",
//                BcryptHasher.hashPassword("testowe"),
//                "admin"))
//        Users.batchInsert(users){ user ->
//            this[Users.email] = user.email
//            this[Users.passwordHash] = user.passwordHash
//            this[Users.role] = user.role
//        }
        createUser("admin@admin.pl", BcryptHasher.hashPassword("admin123"), "admin")
        Unit
    }

    //Users
    override fun createUser(email: String, passwordHash: String, role: String) = transaction(db) {
        if(getUser(email) == null) {
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
        Users.select { Users.email eq email}.mapNotNull {
            if (BCrypt.checkpw(password, it[Users.passwordHash])) {
                User(it[Users.id], it[Users.email], it[Users.passwordHash], it[Users.role])
            } else {
                null
            }
        }.singleOrNull()
    }

    //Universities
    override fun createUniversity(name: String, city: String, urlAddress: String) = transaction(db) {
        Universities.insert {
            it[Universities.name] = name
            it[Universities.city] = city
            it[Universities.urlAddress] = urlAddress
        }
        Unit
    }

    override fun updateUniversity(id: Int, name: String, city: String, urlAddress: String) = transaction(db) {
        Universities.update({Universities.id eq id}){
            it[Universities.name] = name
            it[Universities.city] = city
            it[Universities.urlAddress] = urlAddress
        }
        Unit
    }

    override fun deleteUniversity(id: Int) = transaction(db){
        Universities.deleteWhere { Universities.id eq id }
        Unit
    }

    override fun getUniversity(id: Int) = transaction(db) {
        Universities.select { Universities.id eq id}.map {
            University(it[Universities.id], it[Universities.name], it[Universities.city], it[Universities.urlAddress])
        }.singleOrNull()
    }

    override fun getAllUniversities() = transaction(db) {
        Universities.selectAll().map {
            University(it[Universities.id], it[Universities.name], it[Universities.city], it[Universities.urlAddress])
        }
    }

    override fun close() {}

}