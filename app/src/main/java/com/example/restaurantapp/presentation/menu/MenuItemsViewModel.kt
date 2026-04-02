package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.MenuItemsRepository
import com.example.restaurantapp.domain.model.MenuItem

class MenuItemsViewModel : ViewModel() {
    private val repository = MenuItemsRepository()
    val menuItems = MutableLiveData<List<MenuItem>>()

    fun loadMenuItems(categoryId: Int) {
        menuItems.value = repository.getMenuItemsByCategoryId(categoryId)
    }
}