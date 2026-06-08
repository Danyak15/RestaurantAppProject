package com.example.restaurantapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteDishRepository {
    fun isAuthorized(): Boolean
    fun observeIsFavorite(dishId: Long): Flow<Boolean>
    fun observeFavoriteDishIds(): Flow<List<Long>>
    suspend fun addToFavorites(dishId: Long): Result<Unit>
    suspend fun removeFromFavorites(dishId: Long): Result<Unit>
    suspend fun pushFavorites(): Result<Unit>
    suspend fun pullFavorites(): Result<Unit>
    suspend fun syncFavorites(): Result<Unit>
    suspend fun clearFavorites(): Result<Unit>
}