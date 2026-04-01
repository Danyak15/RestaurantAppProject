package com.example.restaurantapp.presentation.restaurants

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.RestaurantsRepository
import com.example.restaurantapp.domain.model.Restaurant

class RestaurantsViewModel : ViewModel() {
    private val repository = RestaurantsRepository()
    val restaurants = MutableLiveData<List<Restaurant>>()

    init {
        loadRestaurants()
    }

    private fun loadRestaurants() {
        restaurants.value = repository.getRestaurants()
    }
}