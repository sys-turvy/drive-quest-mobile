package com.example.drivequest.presentation.drivehistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivequest.domain.usecase.GetDriveHistoriesUseCase
import com.example.drivequest.presentation.drivehistory.model.DriveHistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriveHistoryViewModel @Inject constructor(
    private val getDriveHistoryUseCase: GetDriveHistoriesUseCase
) : ViewModel() {
    private val _driveHistories = MutableStateFlow<List<DriveHistoryUiState>>(emptyList())
    val driveHistories: StateFlow<List<DriveHistoryUiState>> = _driveHistories.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadHistories() {
        viewModelScope.launch {
            val result = getDriveHistoryUseCase()

            result
                .onSuccess { historyList ->
                    _driveHistories.value = historyList
                }
                .onFailure { e ->
                    _error.value = e.message ?: "不明なエラーが発生しました"
                }
        }
    }
}