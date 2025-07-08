package com.example.drivequest.domain.mapper

import com.example.drivequest.data.remote.api.drivehistory.model.DriveHistoryResponse
import com.example.drivequest.domain.model.DriveHistory

fun DriveHistoryResponse.toDomain(): DriveHistory {
    return DriveHistory(
        id = drivingHistoryId,
        distanceKm = drivingMileage.toDouble(),
        durationMinutes = durationTime,
        timestamp = createdAt
    )
}