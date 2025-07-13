package com.example.drivequest.presentation.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivequest.domain.usecase.GetMonthlyRankingUseCase
import com.example.drivequest.domain.usecase.GetWeeklyRankingUseCase
import com.example.drivequest.presentation.ranking.model.RankingUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getWeeklyRankingUseCase: GetWeeklyRankingUseCase,
    private val getMonthlyRankingUseCase: GetMonthlyRankingUseCase
) : ViewModel() {
    private val _weeklyRanking = MutableStateFlow<List<RankingUiModel>>(emptyList())
    val weeklyRanking: StateFlow<List<RankingUiModel>> = _weeklyRanking.asStateFlow()

    private val _weeklyMyRanking = MutableStateFlow<RankingUiModel?>(null)
    val weeklyMyRanking: StateFlow<RankingUiModel?> = _weeklyMyRanking.asStateFlow()

    private val _monthlyRanking = MutableStateFlow<List<RankingUiModel>>(emptyList())
    val monthlyRanking: StateFlow<List<RankingUiModel>> = _monthlyRanking.asStateFlow()

    private val _monthlyMyRanking = MutableStateFlow<RankingUiModel?>(null)
    val monthlyMyRanking: StateFlow<RankingUiModel?> = _monthlyMyRanking.asStateFlow()


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadWeeklyRanking() {
        viewModelScope.launch {
            val result = getWeeklyRankingUseCase()
            result
                .onSuccess { rankingUiState ->
                    _weeklyRanking.value = rankingUiState.rankingList
                    _weeklyMyRanking.value = rankingUiState.myRanking
                }
                .onFailure { e ->
                    _error.value = e.message ?: "ランキングの取得に失敗しました"
                }
        }
    }

    fun loadMonthlyRanking() {
        viewModelScope.launch {
            val result = getMonthlyRankingUseCase()
            result
                .onSuccess { rankingUiState ->
                    _monthlyRanking.value = rankingUiState.rankingList
                    _monthlyMyRanking.value = rankingUiState.myRanking
                }
                .onFailure { e ->
                    _error.value = e.message ?: "ランキングの取得に失敗しました"
                }
        }
    }
}