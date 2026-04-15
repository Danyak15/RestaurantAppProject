package com.example.restaurantapp.presentation.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.domain.repository.RestaurantsRepository

class InfoViewModelFactory(
    private val repository: RestaurantsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfoViewModel::class.java)) {
            return InfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}