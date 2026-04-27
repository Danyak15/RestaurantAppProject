package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.local.dao.FavoriteDishDao
import com.example.restaurantapp.data.local.entity.FavoriteDishEntity
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FavoriteDishRepositoryImpl(
    private val favoriteDishDao: FavoriteDishDao,
    private val sessionManager: SessionManager
) : FavoriteDishRepository {
    override fun isAuthorized(): Boolean = sessionManager.isAuthorized()

    override fun observeIsFavorite(dishId: Int): Flow<Boolean> {
        val email = sessionManager.getEmail() ?: return flowOf(false)
        return favoriteDishDao.observeIsFavorite(email, dishId)
    }

    override fun observeFavoriteDishIds(): Flow<List<Int>> {
        val email = sessionManager.getEmail() ?: return flowOf(emptyList())
        return favoriteDishDao.observeFavoriteDishIds(email)
    }

    override suspend fun addToFavorites(dishId: Int): Result<Unit> {
        return try {
            val email = sessionManager.getEmail()
                ?: return Result.failure(Exception("Пользователь не авторизован"))

            favoriteDishDao.addToFavorites(FavoriteDishEntity(email, dishId))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFromFavorites(dishId: Int): Result<Unit> {
        return try {
            val email = sessionManager.getEmail()
                ?: return Result.failure(Exception("Пользователь не авторизован"))

            favoriteDishDao.removeFromFavorites(email, dishId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearFavorites(): Result<Unit> {
        return try {
            val email = sessionManager.getEmail()
                ?: return Result.failure(Exception("Пользователь не авторизован"))

            favoriteDishDao.clearFavorites(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}