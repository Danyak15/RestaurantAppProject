package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.domain.repository.DishesRepository

class DishDetailsViewModelFactory(
    private val repository: DishesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DishDetailsViewModel::class.java)) {
            return DishDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}