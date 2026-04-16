package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.domain.repository.AuthRepository
import com.example.restaurantapp.domain.repository.CategoriesRepository
import com.example.restaurantapp.domain.repository.DishesRepository
import com.example.restaurantapp.presentation.menu.MenuCategoriesViewModel

class AuthViewModelFactory(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authRepository, sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}