package com.example.drivequest.domain.repository

import com.example.drivequest.domain.model.PlaceAPIResult
import com.google.android.gms.maps.model.LatLng


interface PlacesRepository {
    suspend fun searchPlaces(query: String): List<PlaceAPIResult>
    suspend fun getPlaceDetails(placeId: String): LatLng?
}
