package com.example.drivequest.domain.usecase

import com.example.drivequest.domain.repository.AuthRepository
import com.example.drivequest.token.TokenManager
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) {
    suspend operator fun invoke(email: String, password: String):Result<Unit> {
        return authRepository.login(email, password).map { loginResponse ->
            tokenManager.saveTokens(loginResponse.tokenPair)
        }
    }
}