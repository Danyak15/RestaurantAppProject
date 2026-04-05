package com.example.restaurantapp.presentation.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.RestaurantsRepositoryImpl
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.RestaurantsRepository

class RestaurantsViewModel(
    private val repository: RestaurantsRepository
) : ViewModel() {
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    init {
        loadRestaurants()
    }

    private fun loadRestaurants() {
        _restaurants.value = repository.getRestaurants()
    }
}