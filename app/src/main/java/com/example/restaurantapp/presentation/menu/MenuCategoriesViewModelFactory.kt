package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.domain.repository.CategoriesRepository
import com.example.restaurantapp.domain.repository.DishesRepository
import com.example.restaurantapp.presentation.info.InfoViewModel

class MenuCategoriesViewModelFactory(
    private val categoriesRepository: CategoriesRepository,
    private val dishesRepository: DishesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuCategoriesViewModel::class.java)) {
            return MenuCategoriesViewModel(categoriesRepository, dishesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}