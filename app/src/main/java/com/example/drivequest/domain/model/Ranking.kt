package com.example.drivequest.domain.model

data class Ranking(
    val rankingList: List<RankingEntry>,
    val myRanking: RankingEntry
)