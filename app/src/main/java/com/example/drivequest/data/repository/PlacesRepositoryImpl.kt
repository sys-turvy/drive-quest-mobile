package com.example.drivequest.data.repository

import android.util.Log
import com.example.drivequest.domain.model.PlaceAPIResult
import com.example.drivequest.domain.repository.PlacesRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.tasks.await

class PlacesRepositoryImpl(private val placesClient: PlacesClient) : PlacesRepository {

    override suspend fun searchPlaces(query: String): List<PlaceAPIResult> {
        if (query.isBlank()) return emptyList()

        // Places APIへのリクエストを作成
        val token = AutocompleteSessionToken.newInstance()
        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(token)
            .setQuery(query)
            .setCountries("JP")
            .build()

        return try {
            val response = placesClient.findAutocompletePredictions(request).await()
            response.autocompletePredictions.map {
               PlaceAPIResult(
                    placeId = it.placeId,
                    primaryText = it.getPrimaryText(null).toString(),
                    secondaryText = it.getSecondaryText(null).toString()
                )
            }
        } catch (e: Exception) {
            Log.e("PlacesRepositoryImpl", "Place search failed", e)
            emptyList()
        }
    }

    override suspend fun getPlaceDetails(placeId: String): LatLng? {
        // 緯度経度(LAT_LNG)の情報だけを取得するように指定
        val placeFields = listOf(Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        return try {
            // APIを呼び出し、緯度経度を返す
            val response = placesClient.fetchPlace(request).await()
            response.place.latLng
        } catch (e: Exception) {
            Log.e("PlacesRepositoryImpl", "Fetch place details failed", e)
            null // エラー時はnullを返す
        }
    }
}
