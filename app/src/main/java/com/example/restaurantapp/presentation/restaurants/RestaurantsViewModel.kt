package com.example.restaurantapp.presentation.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.RestaurantsRepository

class RestaurantsViewModel(
    private val repository: RestaurantsRepository
) : ViewModel() {
    val restaurants: LiveData<List<Restaurant>> = repository.getRestaurants().asLiveData()
}