package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.mock.MockRestaurants
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.RestaurantsRepository

class RestaurantsRepositoryImpl : RestaurantsRepository {
    override fun getRestaurants(): List<Restaurant> {
        return MockRestaurants.getRestaurants()
    }

    override fun getRestaurantById(id: Int): Restaurant? {
        return MockRestaurants.getRestaurantById(id)
    }
}