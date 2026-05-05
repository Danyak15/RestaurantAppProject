package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.dao.RestaurantDao
import com.example.restaurantapp.data.local.mapper.toDomain
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantDao: RestaurantDao
) : RestaurantsRepository {
    override fun getRestaurants(): Flow<List<Restaurant>> {
        return restaurantDao.getRestaurants().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getRestaurantById(id: Int): Restaurant? {
        return restaurantDao.getRestaurantById(id)?.toDomain()
    }
}