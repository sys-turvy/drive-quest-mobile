package com.example.drivequest.model

data class LoginResponse(
    val token: String,
    val user: User
)

data class User(
    val id: String,
    val userId: Int,
    val email: String,
    val userName: String,
    val userIconUrl: String
)