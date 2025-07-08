package com.example.drivequest.data.repository

import com.example.drivequest.domain.repository.NavigationRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.navigation.Navigator
import com.google.android.libraries.navigation.Waypoint

object NavigationRepositoryImpl : NavigationRepository {

    private var navigator: Navigator? = null

    override fun initialize(navigator: Navigator) {
        this.navigator = navigator
        this.navigator?.setAudioGuidance(Navigator.AudioGuidance.VOICE_ALERTS_AND_GUIDANCE)
    }

    override fun setDestination(destination: LatLng, title: String, onRouteStatus: (Navigator.RouteStatus) -> Unit) {
        navigator?.let { nav ->
            nav.clearDestinations()
            val waypoint = Waypoint.builder()
                .setLatLng(destination.latitude, destination.longitude)
                .setTitle(title)
                .build()

            val pendingRoute = nav.setDestination(waypoint)
            pendingRoute?.setOnResultListener { code ->
                onRouteStatus(code)
            }
        }
    }

    override fun startGuidance() {
        navigator?.startGuidance()
    }

    override fun stopGuidance() {
        navigator?.stopGuidance()
        navigator?.clearDestinations()
    }
}
