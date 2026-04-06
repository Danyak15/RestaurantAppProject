package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.repository.CategoriesRepository
import com.example.restaurantapp.domain.repository.DishesRepository

class MenuCategoriesViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val dishesRepository: DishesRepository
) : ViewModel() {
    private var allCategories: List<Category> = emptyList()
    private var allDishes: List<Dish> = emptyList()

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _foundDishes = MutableLiveData<List<Dish>>()
    val foundDishes: LiveData<List<Dish>> = _foundDishes

    private val _searchText = MutableLiveData<String>("")
    val searchText: LiveData<String> = _searchText

    fun loadCategories(restaurantId: Int) {
        allCategories = categoriesRepository.getCategoriesByRestaurantId(restaurantId)
        allDishes = allCategories.flatMap { category ->
            dishesRepository.getDishesByCategoryId(category.id)
        }

        _categories.value = allCategories
        _foundDishes.value = emptyList()
    }

    fun onSearchTextChanged(newSearchText: String) {
        val trimmedText = newSearchText.trim()
        _searchText.value = trimmedText

        _foundDishes.value =
            if (trimmedText.isBlank()) {
                emptyList()
            } else {
                allDishes.filter { dish ->
                    dish.name.contains(trimmedText, ignoreCase = true)
                }
            }
    }
}