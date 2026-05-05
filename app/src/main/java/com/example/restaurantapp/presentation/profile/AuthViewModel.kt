package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _isAuthSuccess = MutableLiveData(false)
    val isAuthSuccess: LiveData<Boolean> = _isAuthSuccess

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Заполни пустые поля"
            return
        }

        viewModelScope.launch {
            val result = accountRepository.login(email, password)

            result.onSuccess {
                _isAuthSuccess.value = true
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Ошибка входа"
            }
        }
    }

    fun register(name: String, surname: String, email: String, password: String) {
        if (name.isBlank() || surname.isBlank() || email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Заполни пустые поля"
            return
        }

        viewModelScope.launch {
            val result = accountRepository.register(name, surname, email, password)

            result.onSuccess {
                _isAuthSuccess.value = true
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Ошибка регистрации"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearAuthSuccess() {
        _isAuthSuccess.value = false
    }
}