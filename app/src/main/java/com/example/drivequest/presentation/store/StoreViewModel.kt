package com.example.drivequest.presentation.store

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivequest.domain.usecase.GetStoreIconFrameUiUseCase
import com.example.drivequest.domain.usecase.GetStoreVoiceStyleUseCase
import com.example.drivequest.presentation.store.model.Product
import com.example.drivequest.presentation.store.model.StoreTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getStoreVoiceStyleUseCase: GetStoreVoiceStyleUseCase,
    private val getStoreIconFrameUiUseCase: GetStoreIconFrameUiUseCase
) : ViewModel() {
    private val _iconFrames = MutableStateFlow<List<Product>>(emptyList())
    val iconFrames: StateFlow<List<Product>> = _iconFrames
    private val _iconFrameError = MutableStateFlow<String?>(null)
    val iconFrameError: StateFlow<String?> = _iconFrameError.asStateFlow()

    private val _voiceStyle = MutableStateFlow<List<Product>>(emptyList())
    val voiceStyle: StateFlow<List<Product>> = _voiceStyle
    private val _voiceStyleError = MutableStateFlow<String?>(null)
    val voiceStyleError: StateFlow<String?> = _voiceStyleError.asStateFlow()

    private val _selectedTab = mutableStateOf<StoreTab>(StoreTab.IconFrame)
    val selectedTab: State<StoreTab> = _selectedTab

    private val _detailTarget = MutableStateFlow<Product?>(null)
    val detailTarget: StateFlow<Product?> = _detailTarget

    private val _purchaseConfirmTarget = MutableStateFlow<Product?>(null)
    val purchaseConfirmTarget: StateFlow<Product?> = _purchaseConfirmTarget

    private val _showAdRemoveDialog = MutableStateFlow<Boolean>(false)
    val showAdRemoveDialog: StateFlow<Boolean> = _showAdRemoveDialog

    fun loadIconFrames() {
        viewModelScope.launch {
            val result = getStoreIconFrameUiUseCase()

            result
                .onSuccess { iconFrames ->
                    _iconFrames.value = iconFrames
                }
                .onFailure { e ->
                    _iconFrameError.value = e.message ?: "不明なエラーが発生しました"
                }
        }
    }

    fun loadVoiceStyle() {
        viewModelScope.launch {
            val result = getStoreVoiceStyleUseCase()

            result
                .onSuccess { voiceStyles ->
                    _voiceStyle.value = voiceStyles
                }
                .onFailure { e ->
                    _voiceStyleError.value = e.message ?: "不明なエラーが発生しました"
                }
        }
    }

    fun selectDetailTarget(target: Product?) {
        _detailTarget.value = target
    }

    fun selectPurchaseConfirmTarget(target: Product?) {
        _purchaseConfirmTarget.value = target
    }

    fun chengeShowAdRemoveDialog(value: Boolean) {
        _showAdRemoveDialog.value = value
    }

    fun selectIconFrameTab() {
        _selectedTab.value = StoreTab.IconFrame
    }

    fun selectVoiceStyleTab() {
        _selectedTab.value = StoreTab.VoiceStyle
    }
}