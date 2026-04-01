package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.CategoriesRepository
import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.model.Restaurant

class MenuCategoriesViewModel : ViewModel() {
    private val repository = CategoriesRepository()
    val categories = MutableLiveData<List<Category>>()

    fun loadCategories(restaurantId: Int) {
        categories.value = repository.getCategoriesByRestaurantId(restaurantId)
    }
}