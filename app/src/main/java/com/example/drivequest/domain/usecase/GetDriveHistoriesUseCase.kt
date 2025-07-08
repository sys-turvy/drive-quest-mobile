package com.example.drivequest.domain.usecase

import com.example.drivequest.domain.repository.DriveHistoryRepository
import com.example.drivequest.presentation.drivehistory.mapper.toUiState
import com.example.drivequest.presentation.drivehistory.model.DriveHistoryUiState
import javax.inject.Inject

class GetDriveHistoriesUseCase @Inject constructor(
    private val driveHistoryRepository: DriveHistoryRepository
) {
    suspend operator fun invoke(): Result<List<DriveHistoryUiState>> {
        return driveHistoryRepository.getDriveHistories().map { responseList ->
            responseList.map { it.toUiState() }
        }
    }
}