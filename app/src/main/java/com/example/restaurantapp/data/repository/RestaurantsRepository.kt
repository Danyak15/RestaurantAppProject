package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.mock.MockRestaurants
import com.example.restaurantapp.domain.model.Restaurant

class RestaurantsRepository {
    fun getRestaurants(): List<Restaurant> {
        return MockRestaurants.getRestaurants()
    }

    fun getRestaurantById(id: Int): Restaurant? {
        return MockRestaurants.getRestaurantById(id)
    }
}