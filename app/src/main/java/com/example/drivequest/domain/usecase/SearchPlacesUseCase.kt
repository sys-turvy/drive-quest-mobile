package com.example.drivequest.domain.usecase

import com.example.drivequest.domain.repository.PlacesRepository

class SearchPlacesUseCase(private val placesRepository: PlacesRepository) {
    suspend operator fun invoke(query: String) = placesRepository.searchPlaces(query)
}
