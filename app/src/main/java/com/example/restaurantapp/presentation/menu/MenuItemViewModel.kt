package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.MenuItemsRepository
import com.example.restaurantapp.domain.model.MenuItem

class MenuItemViewModel : ViewModel() {
    private val repository = MenuItemsRepository()
    val menuItem = MutableLiveData<MenuItem>()

    fun loadMenuItem(menuItemId: Int) {
        menuItem.value = repository.getMenuItemById(menuItemId)
    }
}