package com.example.drivequest.presentation.drivehistory.model

data class DriveHistoryUiState(
    val date: String,
    val startTime: String,
    val endTime: String,
    val durationTime: Int,
    val distance: Double
)