package com.wsbinz.model

import io.ktor.auth.*

data class User(
    val id: Int,
    val email: String,
    val passwordHash: String,
    val role: String
) : Principal