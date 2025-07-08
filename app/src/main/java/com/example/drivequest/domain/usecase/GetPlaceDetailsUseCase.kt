package com.example.drivequest.domain.usecase

import com.example.drivequest.domain.repository.PlacesRepository

class GetPlaceDetailsUseCase(private val placesRepository: PlacesRepository) {
    suspend operator fun invoke(placeId: String) = placesRepository.getPlaceDetails(placeId)
}