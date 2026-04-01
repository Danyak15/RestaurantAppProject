package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuItemsViewModel : ViewModel() {
    val categoryId = MutableLiveData<Int>()

    fun loadItems(id: Int) {
        categoryId.value = id
    }
}