package com.example.restaurantapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteDishRepository {
    fun isAuthorized(): Boolean
    fun observeIsFavorite(dishId: Int): Flow<Boolean>
    fun observeFavoriteDishIds(): Flow<List<Int>>
    suspend fun addToFavorites(dishId: Int): Result<Unit>
    suspend fun removeFromFavorites(dishId: Int): Result<Unit>
    suspend fun pushFavorites(): Result<Unit>
    suspend fun pullFavorites(): Result<Unit>
    suspend fun syncFavorites(): Result<Unit>
    suspend fun clearFavorites(): Result<Unit>
}