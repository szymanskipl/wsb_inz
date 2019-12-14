package com.wsbinz.model

data class Course (
    val id: Int,
    val name: String,
    val description: String,
    val category: Category
)

enum class Category {K1, K2, K3}