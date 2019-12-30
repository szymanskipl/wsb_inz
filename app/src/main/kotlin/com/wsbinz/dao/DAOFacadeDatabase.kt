package com.wsbinz.dao

import com.wsbinz.auth.BcryptHasher
import com.wsbinz.model.Course
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
    fun userVerify(email: String, password: String): User?
    //Universities
    fun createUniversity(name: String, city: String, urlAddress: String)
    fun updateUniversity(id: Int, name: String, city: String, urlAddress: String)
    fun deleteUniversity(id: Int)
    fun getUniversity(id: Int): University?
    fun getAllUniversities(): List<University>
    //Courses
    fun createCourse(name: String, description: String, category: String)
    fun updateCourse(id: Int, name: String, description: String, category: String)
    fun deleteCourse(id: Int)
    fun getCourse(id: Int): Course?
    fun getAllCourses(): List<Course>

}

class DAOFacadeDatabase(val db: Database) : DAOFacade {

    //init database
    override fun init() = transaction(db) {
//        exec("CREATE TYPE CategoryEnum AS ENUM ('K1', 'K2', 'K3');")
        SchemaUtils.create(*tables)
        createUser("admin@admin.pl", BcryptHasher.hashPassword("admin123"), "admin")
//        createCourse("Informatyka inżynierska", "To jest informatyka", Category.K1)
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

    //Courses
    override fun createCourse(name: String, description: String, category: String) = transaction(db) {
        Courses.insert {
            it[Courses.name] = name
            it[Courses.description] = description
            it[Courses.category] = category
        }
        Unit
    }

    override fun updateCourse(id: Int, name: String, description: String, category: String)= transaction(db) {
        Courses.update({Courses.id eq id}) {
            it[Courses.name] = name
            it[Courses.description] = description
            it[Courses.category] = category
        }
        Unit
    }

    override fun deleteCourse(id: Int)= transaction(db) {
        Courses.deleteWhere { Courses.id eq id }
        Unit
    }

    override fun getCourse(id: Int) = transaction(db) {
        Courses.select {Courses.id eq id}.map {
            Course(it[Courses.id], it[Courses.name], it[Courses.description], it[Courses.category])
        }.singleOrNull()
    }

    override fun getAllCourses() = transaction(db) {
        Courses.selectAll().map {
            Course(it[Courses.id], it[Courses.name], it[Courses.description], it[Courses.category])
        }
    }

    override fun close() {}

}