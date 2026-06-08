package com.example.restaurantapp.domain.repository

interface SyncRepository {
    suspend fun sync(): Result<Unit>
}