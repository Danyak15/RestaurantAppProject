package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantsRepository {
    fun getRestaurants(): Flow<List<Restaurant>>
    suspend fun getRestaurantById(id: Long): Restaurant?
}