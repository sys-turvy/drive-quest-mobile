package com.example.drivequest.domain.usecase


import com.example.drivequest.domain.repository.NavigationRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.navigation.Navigator

class SetDestinationUseCase(private val navigationRepository: NavigationRepository) {
    operator fun invoke(
        destination: LatLng,
        title: String,
        onRouteStatus: (Navigator.RouteStatus) -> Unit
    ) {
        navigationRepository.setDestination(destination, title, onRouteStatus)
    }
}
