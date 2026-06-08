package com.example.restaurantapp.domain.usecase.sync

import com.example.restaurantapp.domain.repository.SyncRepository
import javax.inject.Inject

class SyncUseCase @Inject constructor(
    private val syncRepository: SyncRepository
) {
    suspend operator fun invoke() = syncRepository.sync()
}
