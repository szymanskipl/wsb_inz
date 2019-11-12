package com.wsbinz.auth

import com.wsbinz.model.User
import org.mindrot.jbcrypt.BCrypt

object BcryptHasher {

    fun checkPassword(attempt: String, hashedPassword: String) = if (BCrypt.checkpw(attempt, hashedPassword)) Boolean
    else throw Exception("Wrong Password")

    fun hashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

}