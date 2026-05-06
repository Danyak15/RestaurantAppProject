package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.data.remote.dto.response.UserResponse
import com.example.restaurantapp.domain.repository.AccountRepository
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val favoriteRepository: FavoriteDishRepository
) : ViewModel() {
    private val _user = MutableLiveData<UserResponse?>()
    val user: LiveData<UserResponse?> = _user

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    private val _isUpdateSuccess = MutableLiveData(false)
    val isUpdateSuccess: LiveData<Boolean> = _isUpdateSuccess


    fun getMe() {
        viewModelScope.launch {
            val result = accountRepository.getMe()

            result.onSuccess { userResponse ->
                _user.value = userResponse
            }.onFailure { error ->
                _toastMessage.value = error.message ?: "Не удалось загрузить профиль"
            }
        }
    }

    fun updateMe(name: String, surname: String, email: String?) {
        if (name.isBlank() || surname.isBlank()) {
            _toastMessage.value = "Заполните пустые поля"
            return
        }

        viewModelScope.launch {
            val result = accountRepository.updateMe(name, surname, email)

            result.onSuccess { user ->
                _user.value = user
                _isUpdateSuccess.value = true
            }.onFailure { error ->
                _toastMessage.value = error.message ?: "Не удалось обновить профиль"
            }
        }
    }

    fun checkAuth() = accountRepository.checkAuth()

    fun clearSession() {
        viewModelScope.launch {
            favoriteRepository.clearFavorites()
            accountRepository.clearSession()
        }
    }

    fun clearError() {
        _toastMessage.value = null
    }

    fun clearSuccess() {
        _isUpdateSuccess.value = false
    }
}