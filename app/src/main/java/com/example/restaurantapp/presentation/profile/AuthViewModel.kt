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

    fun login(phone: String, password: String) {
        if (phone.isBlank() || password.isBlank()) {
            _errorMessage.value = "Заполни пустые поля"
            return
        }

        viewModelScope.launch {
            val result = accountRepository.login(phone, password)

            result.onSuccess {
                _isAuthSuccess.value = true
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Ошибка при входе"
            }
        }
    }

    fun register(name: String, surname: String, phone: String, password: String) {
        if (name.isBlank() || surname.isBlank() || phone.isBlank() || password.isBlank()) {
            _errorMessage.value = "Заполни пустые поля"
            return
        }

        viewModelScope.launch {
            val registerResult = accountRepository.register(name, surname, phone, password)

            registerResult.onSuccess {
                val loginResult = accountRepository.login(phone, password)

                loginResult.onSuccess {
                    _isAuthSuccess.value = true
                }.onFailure { error ->
                    _errorMessage.value = error.message ?: "Ошибка при входе после регистрации"
                }
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Ошибка при регистрации"
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