package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.MenuItemsRepositoryImpl
import com.example.restaurantapp.domain.model.MenuItem
import com.example.restaurantapp.domain.repository.MenuItemsRepository

class MenuItemViewModel(
    private val repository: MenuItemsRepository
) : ViewModel() {
    private val _menuItem = MutableLiveData<MenuItem>()
    val menuItem: LiveData<MenuItem> = _menuItem

    fun loadMenuItem(menuItemId: Int) {
        _menuItem.value = repository.getMenuItemById(menuItemId)
    }
}