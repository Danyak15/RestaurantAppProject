package com.example.restaurantapp.presentation.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.RestaurantsRepositoryImpl
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.RestaurantsRepository

class InfoViewModel(
    private val repository: RestaurantsRepository
) : ViewModel() {
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    fun loadRestaurant(restaurantId: Int) {
        _restaurant.value = repository.getRestaurantById(restaurantId)
    }
}