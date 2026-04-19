package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.remote.dto.response.UserResponse
import com.example.restaurantapp.domain.repository.AccountRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val accountRepository: AccountRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _user = MutableLiveData<UserResponse?>()
    val user: LiveData<UserResponse?> = _user

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    private val _isUpdateSuccess = MutableLiveData(false)
    val isUpdateSuccess: LiveData<Boolean> = _isUpdateSuccess


    fun getMe() {
        val email = sessionManager.getEmail()
        val password = sessionManager.getPassword()

        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            _toastMessage.value = "Пользователь не авторизован"
            return
        }

        viewModelScope.launch {
            val result = accountRepository.getMe(email, password)

            result.onSuccess { userResponse ->
                _user.value = userResponse
            }.onFailure { error ->
                _toastMessage.value = error.message ?: "Не удалось загрузить профиль"
            }
        }
    }

    fun updateMe(name: String, surname: String, email: String) {
        val password = sessionManager.getPassword()

        if (password.isNullOrBlank()) {
            _toastMessage.value = "Пользователь не авторизован"
            return
        }

        if (name.isBlank() || surname.isBlank() || email.isBlank()) {
            _toastMessage.value = "Заполните пустые поля"
            return
        }

        viewModelScope.launch {
            val result = accountRepository.updateMe(name, surname, email)

            result.onSuccess { user ->
                _user.value = user
                sessionManager.saveCredentials(user.email, password)
                _isUpdateSuccess.value = true
            }.onFailure { error ->
                _toastMessage.value = error.message ?: "Не удалось обновить профиль"
            }
        }
    }

    fun checkAuth() = sessionManager.isAuthorized()

    fun clearSession() = sessionManager.clearSession()

    fun clearError() {
        _toastMessage.value = null
    }

    fun clearSuccess() {
        _isUpdateSuccess.value = false
    }
}