package com.example.drivequest.data.remote.api.weather

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 位置更新を監視し、一定条件で天気 API を再取得して流すヘルパー。
 */
class WeatherManager(
    private val context: Context,
    private val apiKey: String,
    private val minDistanceMeters: Float = 300f,          // 300 m 移動したら更新
    private val minIntervalMillis: Long = 10 * 60_000L    // 10 分経過したら更新
) {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)
    private val repository  = WeatherRepository(apiKey)
    private val scope       = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var lastLocation: Location? = null
    private var lastFetchTime: Long = 0L

    private val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { checkAndUpdateWeather(it) }
        }
    }

    /** 監視開始 */
    @SuppressLint("MissingPermission")
    fun start() {
        // 初回：キャッシュ位置で天気取得
        scope.launch {
            LocationUtils.getLastKnownLocation(context)?.let { checkAndUpdateWeather(it, force = true) }
        }

        val request = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            10_000L         // 10 秒ごとに位置イベント受信
        ).setMinUpdateDistanceMeters(minDistanceMeters)
            .build()

        fusedClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
    }

    /** 監視終了（Lifecycle に合わせて呼ぶ） */
    fun stop() {
        fusedClient.removeLocationUpdates(callback)
        scope.cancel()
    }

    private fun checkAndUpdateWeather(newLoc: Location, force: Boolean = false) {
        val moved   = lastLocation?.distanceTo(newLoc) ?: Float.MAX_VALUE
        val elapsed = System.currentTimeMillis() - lastFetchTime

        if (force || moved >= minDistanceMeters || elapsed >= minIntervalMillis) {
            lastLocation  = newLoc
            lastFetchTime = System.currentTimeMillis()
            scope.launch {
                try {
                    _weather.emit(repository.getWeather(newLoc.latitude, newLoc.longitude))
                } catch (_: Exception) {
                    // ネットワーク失敗などは無視
                }
            }
        }
    }
}
