package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.MenuItemsRepository
import com.example.restaurantapp.domain.model.MenuItem

class MenuItemViewModel : ViewModel() {
    private val repository = MenuItemsRepository()
    private val _menuItem = MutableLiveData<MenuItem>()
    val menuItem: LiveData<MenuItem> = _menuItem

    fun loadMenuItem(menuItemId: Int) {
        _menuItem.value = repository.getMenuItemById(menuItemId)
    }
}