package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Restaurant

interface RestaurantsRepository {
    suspend fun getRestaurants(): List<Restaurant>
    suspend fun getRestaurantById(id: Long): Restaurant?
}
