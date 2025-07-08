package com.example.drivequest.domain.usecase

import com.example.drivequest.domain.repository.NavigationRepository

class StopGuidanceUseCase(private val navigationRepository: NavigationRepository) {
    operator fun invoke() {
        navigationRepository.stopGuidance()
    }
}