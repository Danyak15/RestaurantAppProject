package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.data.remote.dto.UserResponse
import com.example.restaurantapp.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _user = MutableLiveData<UserResponse?>()
    val user: LiveData<UserResponse?> = _user

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadMe(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.getMe(email, password)

            result.onSuccess { userResponse ->
                _user.value = userResponse
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Не удалось загрузить профиль"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}