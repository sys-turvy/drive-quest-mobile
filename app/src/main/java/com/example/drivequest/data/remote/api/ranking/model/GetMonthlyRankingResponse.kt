package com.example.drivequest.data.remote.api.ranking.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetMonthlyRankingResponse(
    val ranking: List<RankingItemDto>,
    val myRank: RankingItemDto
)
