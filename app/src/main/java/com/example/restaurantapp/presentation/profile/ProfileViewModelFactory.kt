package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.domain.repository.AccountRepository
import com.example.restaurantapp.domain.repository.FavoriteDishRepository

class ProfileViewModelFactory(
    private val accountRepository: AccountRepository,
    private val favoriteRepository: FavoriteDishRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(accountRepository, favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}