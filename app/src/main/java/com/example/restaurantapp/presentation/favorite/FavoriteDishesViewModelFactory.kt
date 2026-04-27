package com.example.restaurantapp.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.domain.repository.DishesRepository
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import com.example.restaurantapp.domain.repository.RestaurantsRepository

class FavoriteDishesViewModelFactory(
    private val restaurantsRepository: RestaurantsRepository,
    private val dishesRepository: DishesRepository,
    private val favoriteDishRepository: FavoriteDishRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteDishesViewModel::class.java)) {
            return FavoriteDishesViewModel(
                restaurantsRepository,
                dishesRepository,
                favoriteDishRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}