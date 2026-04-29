package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.local.dao.FavoriteDishDao
import com.example.restaurantapp.data.local.dao.FavoriteSyncDao
import com.example.restaurantapp.data.local.entity.FavoriteDishEntity
import com.example.restaurantapp.data.local.entity.FavoriteSyncEntity
import com.example.restaurantapp.data.remote.api.FavoriteApi
import com.example.restaurantapp.data.utils.NetworkHelper
import com.example.restaurantapp.data.worker.FavoriteSyncScheduler
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FavoriteDishRepositoryImpl(
    private val favoriteDishDao: FavoriteDishDao,
    private val favoriteSyncDao: FavoriteSyncDao,
    private val favoriteApi: FavoriteApi,
    private val sessionManager: SessionManager,
    private val networkHelper: NetworkHelper,
    private val favoriteSyncScheduler: FavoriteSyncScheduler
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
        val email = sessionManager.getEmail()
            ?: return Result.failure(Exception("Пользователь не авторизован"))

        favoriteDishDao.addToFavorites(
            FavoriteDishEntity(
                userEmail = email,
                dishId = dishId
            )
        )

        val existingTask = favoriteSyncDao.getTask(email, dishId)

        if (existingTask?.action == "REMOVE") {
            favoriteSyncDao.removeTask(email, dishId)
        } else {
            favoriteSyncDao.addTask(
                FavoriteSyncEntity(
                    userEmail = email,
                    dishId = dishId,
                    action = "ADD"
                )
            )
        }

        return trySyncItem(dishId, "ADD")
    }

    override suspend fun removeFromFavorites(dishId: Int): Result<Unit> {
        val email = sessionManager.getEmail()
            ?: return Result.failure(Exception("Пользователь не авторизован"))

        favoriteDishDao.removeFromFavorites(email, dishId)

        val existingTask = favoriteSyncDao.getTask(email, dishId)

        if (existingTask?.action == "ADD") {
            favoriteSyncDao.removeTask(email, dishId)
        } else {
            favoriteSyncDao.addTask(
                FavoriteSyncEntity(
                    userEmail = email,
                    dishId = dishId,
                    action = "REMOVE"
                )
            )
        }

        return trySyncItem(dishId, "REMOVE")
    }

    private suspend fun trySyncItem(dishId: Int, action: String): Result<Unit> {
        val email = sessionManager.getEmail()
            ?: return Result.failure(Exception("Пользователь не авторизован"))

        return try {
            networkHelper.checkInternetConnection()

            val response = when (action) {
                "ADD" -> favoriteApi.addToFavorites(dishId)
                "REMOVE" -> favoriteApi.removeFromFavorites(dishId)
                else -> return Result.failure(Exception("Неизвестная команда"))
            }

            if (response.isSuccessful) {
                favoriteSyncDao.removeTask(email, dishId)
            } else {
                favoriteSyncScheduler.schedule()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            favoriteSyncScheduler.schedule()
            Result.success(Unit)
        }
    }

    override suspend fun pushFavorites(): Result<Unit> {
        val email = sessionManager.getEmail()
            ?: return Result.failure(Exception("Пользователь не авторизован"))

        return try {
            networkHelper.checkInternetConnection()

            val tasks = favoriteSyncDao.getTasks(email)
            var allSuccessful = true

            tasks.forEach { task ->
                val response = when (task.action) {
                    "ADD" -> favoriteApi.addToFavorites(task.dishId)
                    "REMOVE" -> favoriteApi.removeFromFavorites(task.dishId)
                    else -> null
                }

                if (response?.isSuccessful == true) {
                    favoriteSyncDao.removeTask(email, task.dishId)
                } else {
                    allSuccessful = false
                }
            }

            if (allSuccessful) {
                Result.success(Unit)
            } else {
                favoriteSyncScheduler.schedule()
                Result.failure(Exception("Не все изменения были обработаны"))
            }
        } catch (e: Exception) {
            favoriteSyncScheduler.schedule()
            Result.failure(e)
        }
    }

    override suspend fun pullFavorites(): Result<Unit> {
        val email = sessionManager.getEmail()
            ?: return Result.failure(Exception("Пользователь не авторизован"))

        return try {
            networkHelper.checkInternetConnection()

            val response = favoriteApi.getFavoriteDishes()

            if (response.isSuccessful && response.body() != null)  {
                val favoriteIds = response.body()!!
                favoriteDishDao.updateFavoriteDishes(email, favoriteIds)

                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка при загрузке избранных блюд: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun syncFavorites(): Result<Unit> {
        val pushResult = pushFavorites()

        return if (pushResult.isFailure) {
            pushResult
        } else {
            pullFavorites()
        }
    }

    override suspend fun clearFavorites(): Result<Unit> {
        val email = sessionManager.getEmail()
            ?: return Result.failure(Exception("Пользователь не авторизован"))

        return try {
            favoriteSyncDao.clearTasks(email)
            favoriteDishDao.clearFavorites(email)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}