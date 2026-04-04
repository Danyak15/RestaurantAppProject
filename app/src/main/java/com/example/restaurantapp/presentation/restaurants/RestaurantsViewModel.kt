package com.example.restaurantapp.presentation.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.RestaurantsRepository
import com.example.restaurantapp.domain.model.Restaurant

class RestaurantsViewModel : ViewModel() {
    private val repository = RestaurantsRepository()
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    init {
        loadRestaurants()
    }

    private fun loadRestaurants() {
        _restaurants.value = repository.getRestaurants()
    }
}