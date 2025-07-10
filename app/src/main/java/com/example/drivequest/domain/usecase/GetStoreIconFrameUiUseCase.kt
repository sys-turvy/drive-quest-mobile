package com.example.drivequest.domain.usecase

import com.example.drivequest.domain.repository.StoreRepository
import com.example.drivequest.presentation.store.mapper.toUiState
import com.example.drivequest.presentation.store.model.Product
import javax.inject.Inject

class GetStoreIconFrameUiUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return storeRepository.getShopIconFrame().map { responseList ->
            responseList.map { it.toUiState() }
        }
    }
}