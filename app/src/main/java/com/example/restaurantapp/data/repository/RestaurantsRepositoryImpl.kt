package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.dao.RestaurantDao
import com.example.restaurantapp.data.local.mapper.toDomain
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.RestaurantsRepository
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantDao: RestaurantDao
) : RestaurantsRepository {
    override suspend fun getRestaurants(): List<Restaurant> {
        return restaurantDao.getRestaurantsWithHours().map { it.toDomain() }
    }

    override suspend fun getRestaurantById(id: Long): Restaurant? {
        return restaurantDao.getRestaurantWithHoursById(id)?.toDomain()
    }
}
