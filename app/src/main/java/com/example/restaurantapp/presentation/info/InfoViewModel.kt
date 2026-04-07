package com.example.restaurantapp.presentation.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.data.repository.RestaurantsRepositoryImpl
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.RestaurantsRepository
import kotlinx.coroutines.launch

class InfoViewModel(
    private val repository: RestaurantsRepository
) : ViewModel() {
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    fun loadRestaurant(restaurantId: Int) {
        viewModelScope.launch {
            _restaurant.value = repository.getRestaurantById(restaurantId)
        }
    }
}