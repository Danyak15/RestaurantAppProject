package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.remote.dto.request.UpdateUserRequest
import com.example.restaurantapp.data.remote.dto.response.UserResponse
import com.example.restaurantapp.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _user = MutableLiveData<UserResponse?>()
    val user: LiveData<UserResponse?> = _user

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    fun getMe() {
        val email = sessionManager.getEmail()
        val password = sessionManager.getPassword()

        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            _toastMessage.value = "Пользователь не авторизован"
            return
        }

        viewModelScope.launch {
            val result = authRepository.getMe(email, password)

            result.onSuccess { userResponse ->
                _user.value = userResponse
            }.onFailure { error ->
                _toastMessage.value = error.message ?: "Не удалось загрузить профиль"
            }
        }
    }

    fun updateMe(name: String, surname: String, email: String) {
        val password = sessionManager.getPassword()

        if (name.isBlank() || surname.isBlank() || email.isBlank()) {
            _toastMessage.value = "Заполните пустые поля"
            return
        }

        viewModelScope.launch {
            val result = authRepository.updateMe(
                UpdateUserRequest(
                    name = name,
                    surname = surname,
                    email = email
                )
            )

            result.onSuccess { user ->
                _user.value = user
                sessionManager.saveCredentials(user.email, password!!)
            }.onFailure { error ->
                _toastMessage.value = error.message ?: "Не удалось обновить профиль"
            }
        }

    }

    fun clearError() {
        _toastMessage.value = null
    }
}