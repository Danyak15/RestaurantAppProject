package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.dao.CategoryDao
import com.example.restaurantapp.data.local.dao.DishDao
import com.example.restaurantapp.data.local.dao.RestaurantDao
import com.example.restaurantapp.data.local.mapper.toEntity
import com.example.restaurantapp.data.local.mapper.toHourEntities
import com.example.restaurantapp.data.remote.api.SyncApi
import com.example.restaurantapp.data.utils.NetworkHelper
import com.example.restaurantapp.domain.repository.SyncRepository
import javax.inject.Inject

class SyncRepositoryImpl @Inject constructor(
    private val syncApi: SyncApi,
    private val networkHelper: NetworkHelper,
    private val restaurantDao: RestaurantDao,
    private val categoryDao: CategoryDao,
    private val dishDao: DishDao
) : SyncRepository {
    override suspend fun sync(): Result<Unit> {
        return try {
            networkHelper.checkInternetConnection()

            val response = syncApi.sync()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                restaurantDao.insertRestaurantsWithHours(
                    restaurants = body.restaurants.map { it.toEntity() },
                    hours = body.restaurants.flatMap { it.toHourEntities() }
                )
                categoryDao.insertAll(body.categories.map { it.toEntity() })
                dishDao.insertAll(body.dishes.map { it.toEntity() })
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка синхронизации"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
