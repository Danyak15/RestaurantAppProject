package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.User
import com.example.restaurantapp.domain.repository.AccountRepository
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val favoriteRepository: FavoriteDishRepository
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _message = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val message: SharedFlow<String> = _message.asSharedFlow()

    private val _updateSuccess = MutableSharedFlow<Unit>()
    val updateSuccess: SharedFlow<Unit> = _updateSuccess.asSharedFlow()


    fun getMe() {
        viewModelScope.launch {
            val result = accountRepository.getMe()

            result.onSuccess { user ->
                _user.value = user
            }.onFailure { error ->
                _message.emit(error.message ?: "Не удалось загрузить профиль")
            }
        }
    }

    fun updateMe(name: String, surname: String, email: String?) {
        if (name.isBlank() || surname.isBlank()) {
            _message.tryEmit("Заполните пустые поля")
            return
        }

        viewModelScope.launch {
            val result = accountRepository.updateMe(name, surname, email)

            result.onSuccess { user ->
                _user.value = user
                _updateSuccess.emit(Unit)
            }.onFailure { error ->
                _message.emit(error.message ?: "Не удалось обновить профиль")
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
}