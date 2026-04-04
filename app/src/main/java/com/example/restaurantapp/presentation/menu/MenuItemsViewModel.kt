package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.MenuItemsRepository
import com.example.restaurantapp.domain.model.MenuItem

class MenuItemsViewModel : ViewModel() {
    private val repository = MenuItemsRepository()
    private val _menuItems = MutableLiveData<List<MenuItem>>()
    val menuItems: LiveData<List<MenuItem>> = _menuItems

    fun loadMenuItems(categoryId: Int) {
        _menuItems.value = repository.getMenuItemsByCategoryId(categoryId)
    }
}