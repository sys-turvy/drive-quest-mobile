package com.example.drivequest.presentation.ranking.model

data class RankingUiModel(
    val rank: Int,
    val name: String,
    val iconUrl: String?,
    val distanceKm: Double
)