package com.example.drivequest.domain.usecase

import com.example.drivequest.domain.repository.RankingRepository
import com.example.drivequest.presentation.ranking.mapper.toUiState
import com.example.drivequest.presentation.ranking.model.RankingListUiState
import javax.inject.Inject

class GetWeeklyRankingUseCase @Inject constructor(
    private val rankingRepository: RankingRepository
) {
    suspend operator fun invoke(): Result<RankingListUiState> {
        return rankingRepository.getWeeklyRanking().map { it.toUiState() }
    }
}