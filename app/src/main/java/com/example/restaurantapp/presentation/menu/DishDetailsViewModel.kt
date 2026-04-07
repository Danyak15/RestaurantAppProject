package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.repository.DishesRepository
import kotlinx.coroutines.launch

class DishDetailsViewModel(
    private val repository: DishesRepository
) : ViewModel() {
    private val _dish = MutableLiveData<Dish>()
    val dish: LiveData<Dish> = _dish

    fun loadDishDetails(menuItemId: Int) {
        viewModelScope.launch {
            _dish.value = repository.getDishById(menuItemId)
        }
    }
}