package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.usecase.menu.GetDishesByCategoryIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DishesViewModel @Inject constructor(
    private val getDishesByCategoryIdUseCase: GetDishesByCategoryIdUseCase
) : ViewModel() {
    private val _dishes = MutableStateFlow<List<Dish>>(emptyList())
    val dishes: StateFlow<List<Dish>> = _dishes.asStateFlow()

    fun loadDishes(categoryId: Long) {
        viewModelScope.launch {
            _dishes.value = getDishesByCategoryIdUseCase(categoryId)
        }
    }
}
