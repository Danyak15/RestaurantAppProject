package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.CategoriesRepository
import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.model.Restaurant

class MenuCategoriesViewModel : ViewModel() {
    private val repository = CategoriesRepository()
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    fun loadCategories(restaurantId: Int) {
        _categories.value = repository.getCategoriesByRestaurantId(restaurantId)
    }
}