package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Restaurant

interface RestaurantsRepository {
    fun getRestaurants(): List<Restaurant>
    fun getRestaurantById(id: Int): Restaurant?
}