package com.wsbinz.model

import io.ktor.auth.*

data class User(
    val email: String,
    val passwordHash: String,
    val role: String
) : Principal