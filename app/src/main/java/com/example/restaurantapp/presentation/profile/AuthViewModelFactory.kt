package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.domain.repository.AccountRepository

class AuthViewModelFactory(
    private val accountRepository: AccountRepository,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(accountRepository, sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}