package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.domain.repository.AuthRepository

class ProfileViewModelFactory(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(authRepository, sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}