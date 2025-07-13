package com.example.drivequest.domain.mapper

import com.example.drivequest.data.remote.api.ranking.model.GetMonthlyRankingResponse
import com.example.drivequest.domain.model.Ranking

fun GetMonthlyRankingResponse.toDomain(): Ranking {
    return Ranking(
        rankingList = this.ranking.map { it.toDomain() },
        myRanking = this.myRank.toDomain()
    )
}