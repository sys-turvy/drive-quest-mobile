package com.example.drivequest.domain.repository

import com.example.drivequest.data.remote.api.auth.model.LoginResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<LoginResponse>
}