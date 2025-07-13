package com.example.drivequest.domain.mapper

import com.example.drivequest.data.remote.api.ranking.model.RankingItemDto
import com.example.drivequest.domain.model.RankingEntry

fun RankingItemDto.toDomain(): RankingEntry {
    return RankingEntry(
        rank = this.rank,
        user = this.user.toDomain(),
        distanceKm = this.distance
    )
}