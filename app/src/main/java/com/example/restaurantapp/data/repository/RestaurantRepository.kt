package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.mock.MockData
import com.example.restaurantapp.domain.model.Restaurant

class RestaurantRepository {
    fun getRestaurants(): List<Restaurant> {
        return MockData.getRestaurants()
    }

    fun getRestaurantById(id: Int): Restaurant? {
        return MockData.getRestaurantById(id)
    }
}