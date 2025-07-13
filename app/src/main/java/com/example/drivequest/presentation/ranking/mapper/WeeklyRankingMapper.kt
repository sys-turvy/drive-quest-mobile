package com.example.drivequest.presentation.ranking.mapper

import com.example.drivequest.domain.model.Ranking
import com.example.drivequest.presentation.ranking.model.RankingListUiState

fun Ranking.toUiState(): RankingListUiState {
    return RankingListUiState(
        rankingList = rankingList.map { it.toUiModel() },
        myRanking = myRanking.toUiModel()
    )
}