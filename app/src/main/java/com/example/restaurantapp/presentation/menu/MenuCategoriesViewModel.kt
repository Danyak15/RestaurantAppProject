package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.repository.CategoriesRepository
import com.example.restaurantapp.domain.repository.DishesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MenuCategoriesViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
    private val dishesRepository: DishesRepository
) : ViewModel() {
    private val _selectedRestaurantId = MutableStateFlow<Int?>(null)

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val categories: StateFlow<List<Category>> = _selectedRestaurantId
        .filterNotNull()
        .flatMapLatest { restaurantId ->
            categoriesRepository.getCategoriesByRestaurantId(restaurantId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val allDishes: StateFlow<List<Dish>> = _selectedRestaurantId
        .filterNotNull()
        .flatMapLatest { restaurantId ->
            dishesRepository.observeDishesByRestaurantId(restaurantId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val foundDishes: StateFlow<List<Dish>> =
        combine(allDishes, searchText) { dishes, text ->
            val trimmedText = text.trim()

            if (trimmedText.isBlank()) {
                emptyList()
            } else {
                dishes.filter { dish ->
                    dish.name.contains(trimmedText, ignoreCase = true)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun loadCategories(restaurantId: Int) {
        _selectedRestaurantId.value = restaurantId
    }

    fun onSearchTextChanged(newSearchText: String) {
        _searchText.value = newSearchText
    }
}