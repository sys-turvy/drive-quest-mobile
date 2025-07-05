package com.example.drivequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivequest.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EntryPointViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    sealed class AppStartupState {
        object Loading : AppStartupState()
        object Authenticated : AppStartupState()
        object Unauthenticated : AppStartupState()
    }

    val appStartupState: StateFlow<AppStartupState> =
        authRepository.getTokenFlow()
            .map { token ->
                if (!token.isNullOrEmpty()) AppStartupState.Authenticated
                else AppStartupState.Unauthenticated
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = AppStartupState.Loading
            )
}