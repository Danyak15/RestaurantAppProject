package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.data.repository.MenuItemsRepositoryImpl
import com.example.restaurantapp.domain.model.MenuItem
import com.example.restaurantapp.domain.repository.MenuItemsRepository

class MenuItemsViewModel(
    private val repository: MenuItemsRepository
) : ViewModel() {
    private val _menuItems = MutableLiveData<List<MenuItem>>()
    val menuItems: LiveData<List<MenuItem>> = _menuItems

    fun loadMenuItems(categoryId: Int) {
        _menuItems.value = repository.getMenuItemsByCategoryId(categoryId)
    }
}