package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.usecase.menu.GetRestaurantMenuUseCase
import com.example.restaurantapp.domain.usecase.menu.SearchDishesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuCategoriesViewModel @Inject constructor(
    private val getRestaurantMenuUseCase: GetRestaurantMenuUseCase,
    private val searchDishesUseCase: SearchDishesUseCase
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val allDishes = MutableStateFlow<List<Dish>>(emptyList())

    val foundDishes: StateFlow<List<Dish>> =
        combine(allDishes, searchText) { dishes, text ->
            searchDishesUseCase(dishes, text)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun loadCategories(restaurantId: Long) {
        viewModelScope.launch {
            val menu = getRestaurantMenuUseCase(restaurantId)
            _categories.value = menu.categories
            allDishes.value = menu.dishes
        }
    }

    fun onSearchTextChanged(newSearchText: String) {
        _searchText.value = newSearchText
    }
}
