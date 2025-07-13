package com.example.drivequest.presentation.ranking.mapper

import com.example.drivequest.domain.model.RankingEntry
import com.example.drivequest.presentation.ranking.model.RankingUiModel

fun RankingEntry.toUiModel(): RankingUiModel {
    return RankingUiModel(
        rank = rank,
        name = user.name,
        iconUrl = user.iconUrl,
        distanceKm = distanceKm
    )
}