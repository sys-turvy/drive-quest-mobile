package com.example.drivequest.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivequest.data.repository.NavigationRepositoryImpl
import com.example.drivequest.data.repository.PlacesRepositoryImpl
import com.example.drivequest.domain.model.PlaceAPIResult
import com.example.drivequest.domain.usecase.GetPlaceDetailsUseCase
import com.example.drivequest.domain.usecase.SearchPlacesUseCase
import com.example.drivequest.domain.usecase.SetDestinationUseCase
import com.example.drivequest.domain.usecase.StartGuidanceUseCase
import com.example.drivequest.domain.usecase.StopGuidanceUseCase
import com.google.android.libraries.navigation.Navigator
import com.google.android.libraries.navigation.TimeAndDistance
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

enum class UiState { SEARCHING, GUIDING, ARRIVED }

data class GuidanceInfo(
    val destinationName: String = "目的地",
    val eta: String = "--:--",
    val time: String = "- 分",
    val distance: String = "-.- km"
)

class HomeViewModel : ViewModel() {

    // --- UseCaseのインスタンス化 ---
    private lateinit var searchPlacesUseCase: SearchPlacesUseCase
    private lateinit var getPlaceDetailsUseCase: GetPlaceDetailsUseCase
    private val setDestinationUseCase = SetDestinationUseCase(NavigationRepositoryImpl)
    private val startGuidanceUseCase = StartGuidanceUseCase(NavigationRepositoryImpl)
    private val stopGuidanceUseCase = StopGuidanceUseCase(NavigationRepositoryImpl)

    private var navigator: Navigator? = null

    // --- UIの状態 ---
    private val _uiState = MutableStateFlow(UiState.SEARCHING)
    val uiState = _uiState.asStateFlow()
    private val _guidanceInfo = MutableStateFlow(GuidanceInfo())
    val guidanceInfo = _guidanceInfo.asStateFlow()
    private val _autocompleteResults = MutableStateFlow<List<PlaceAPIResult>>(emptyList())
    val autocompleteResults = _autocompleteResults.asStateFlow()

    private var searchJob: Job? = null
    private var arrivalListener: Navigator.ArrivalListener? = null
    private var remainingTimeOrDistanceChangedListener: Navigator.RemainingTimeOrDistanceChangedListener? = null

    /**
     * Viewからの準備完了通知を受け、すべての機能を初期化する
     */
    fun onReady(placesClient: PlacesClient, navigator: Navigator) {
        val placesRepository = PlacesRepositoryImpl(placesClient)
        searchPlacesUseCase = SearchPlacesUseCase(placesRepository)
        getPlaceDetailsUseCase = GetPlaceDetailsUseCase(placesRepository)
        this.navigator = navigator
        NavigationRepositoryImpl.initialize(navigator)
        registerNavigationListeners()
    }

    fun onSearchQueryChanged(query: String) {
        if (!::searchPlacesUseCase.isInitialized) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            _autocompleteResults.value = if (query.isNotBlank()) searchPlacesUseCase(query) else emptyList()
        }
    }

    fun onPlaceSelected(place: PlaceAPIResult) {
        if (!::getPlaceDetailsUseCase.isInitialized) return
        viewModelScope.launch {
            getPlaceDetailsUseCase(place.placeId)?.let { destination ->
                // UseCaseに目的地設定を依頼し、結果をコールバックで受け取る
                setDestinationUseCase(destination, place.primaryText) { routeStatus ->
                    // ★★★ 型が Navigator.RouteStatus になったことで、より安全な比較が可能 ★★★
                    if (routeStatus == Navigator.RouteStatus.OK) {
                        _uiState.value = UiState.GUIDING
                        _guidanceInfo.value = _guidanceInfo.value.copy(destinationName = place.primaryText)
                        startGuidanceUseCase()
                    }
                }
            }
            _autocompleteResults.value = emptyList()
        }
    }

    fun onGuidanceCompleted() {
        stopGuidanceUseCase()
        _uiState.value = UiState.SEARCHING
        _guidanceInfo.value = GuidanceInfo()
    }

    private fun registerNavigationListeners() {
        arrivalListener = Navigator.ArrivalListener {
            _uiState.value = UiState.ARRIVED
        }
        navigator?.addArrivalListener(arrivalListener!!)

        remainingTimeOrDistanceChangedListener =
            Navigator.RemainingTimeOrDistanceChangedListener {
                navigator?.currentTimeAndDistance?.let { updateGuidanceInfo(it) }
            }
        navigator?.addRemainingTimeOrDistanceChangedListener(1, 1, remainingTimeOrDistanceChangedListener!!)
    }

    private fun updateGuidanceInfo(timeAndDistance: TimeAndDistance) {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val eta = timeFormat.format(Date(System.currentTimeMillis() + (timeAndDistance.seconds * 1000)))
        val totalMinutes = TimeUnit.SECONDS.toMinutes(timeAndDistance.seconds.toLong())
        val time = if (totalMinutes >= 60) {
            val hours = totalMinutes / 60
            val minutes = totalMinutes % 60
            "${hours}時間 ${minutes}分"
        } else {
            "$totalMinutes 分"
        }
        val distance = String.format(Locale.getDefault(), "%.1f km", timeAndDistance.meters / 1000.0)
        _guidanceInfo.value = _guidanceInfo.value.copy(
            eta = eta,
            time = time,
            distance = distance
        )
    }

    override fun onCleared() {
        navigator?.let { nav ->
            arrivalListener?.let { nav.removeArrivalListener(it) }
            remainingTimeOrDistanceChangedListener?.let { nav.removeRemainingTimeOrDistanceChangedListener(it) }
            nav.cleanup()
        }
        super.onCleared()
    }
}
