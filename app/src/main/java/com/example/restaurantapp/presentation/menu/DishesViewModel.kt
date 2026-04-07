package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.repository.DishesRepository
import kotlinx.coroutines.launch

class DishesViewModel(
    private val repository: DishesRepository
) : ViewModel() {
    private val _dishes = MutableLiveData<List<Dish>>()
    val dishes: LiveData<List<Dish>> = _dishes

    fun loadDishes(categoryId: Int) {
        viewModelScope.launch {
            repository.getDishesByCategoryId(categoryId).collect { items ->
                _dishes.value = items
            }
        }
    }
}