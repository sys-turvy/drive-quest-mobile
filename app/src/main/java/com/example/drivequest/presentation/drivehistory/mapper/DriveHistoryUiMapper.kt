package com.example.drivequest.presentation.drivehistory.mapper

import com.example.drivequest.domain.model.DriveHistory
import com.example.drivequest.presentation.drivehistory.model.DriveHistoryUiState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun DriveHistory.toUiState(): DriveHistoryUiState {
    val zoneId = ZoneId.systemDefault()
    val instant = Instant.parse(timestamp)
    val zonedDateTime = instant.atZone(zoneId)

    val formatterDate = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    val formatterTime = DateTimeFormatter.ofPattern("HH:mm")

    val date = zonedDateTime.format(formatterDate)
    val startTime = zonedDateTime.format(formatterTime)
    val endTime = zonedDateTime.plusMinutes(durationMinutes.toLong()).format(formatterTime)

    return DriveHistoryUiState(
        date = date,
        startTime = startTime,
        endTime = endTime,
        durationTime = durationMinutes,
        distance = distanceKm
    )
}