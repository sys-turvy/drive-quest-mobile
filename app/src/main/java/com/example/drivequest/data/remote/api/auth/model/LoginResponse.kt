package com.example.drivequest.data.remote.api.auth.model

import com.example.drivequest.domain.model.TokenPair

data class LoginResponse(
    val tokenPair: TokenPair,
    val user: User
)

data class User(
    val id: String,
    val userId: Int,
    val email: String,
    val userName: String,
    val userIconUrl: String?
)