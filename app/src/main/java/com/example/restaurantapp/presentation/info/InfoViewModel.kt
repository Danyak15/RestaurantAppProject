package com.example.restaurantapp.presentation.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.RestaurantsRepository
import com.example.restaurantapp.domain.model.Restaurant

class InfoViewModel : ViewModel() {
    private val repository = RestaurantsRepository()
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    fun loadRestaurant(restaurantId: Int) {
        _restaurant.value = repository.getRestaurantById(restaurantId)
    }
}