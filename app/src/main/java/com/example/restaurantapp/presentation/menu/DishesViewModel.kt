package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.repository.DishesRepository

class DishesViewModel(
    private val repository: DishesRepository
) : ViewModel() {
    private val _dishes = MutableLiveData<List<Dish>>()
    val dishes: LiveData<List<Dish>> = _dishes

    fun loadDishes(categoryId: Int) {
        _dishes.value = repository.getDishesByCategoryId(categoryId)
    }
}