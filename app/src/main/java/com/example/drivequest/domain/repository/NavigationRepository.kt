package com.example.drivequest.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.navigation.Navigator

/**
 * ナビゲーション操作のインターフェース
 */
interface NavigationRepository {
    fun initialize(navigator: Navigator)
    fun setDestination(destination: LatLng, title: String, onRouteStatus: (Navigator.RouteStatus) -> Unit)
    fun startGuidance()
    fun stopGuidance()
}
