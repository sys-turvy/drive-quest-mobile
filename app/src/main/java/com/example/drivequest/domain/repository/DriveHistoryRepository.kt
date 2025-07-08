package com.example.drivequest.domain.repository

import com.example.drivequest.domain.model.DriveHistory

interface DriveHistoryRepository {
    suspend fun getDriveHistories(): Result<List<DriveHistory>>
}