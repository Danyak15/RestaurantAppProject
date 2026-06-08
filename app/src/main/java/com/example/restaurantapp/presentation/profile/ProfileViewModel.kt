package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.User
import com.example.restaurantapp.domain.usecase.account.CheckAuthUseCase
import com.example.restaurantapp.domain.usecase.account.GetMeUseCase
import com.example.restaurantapp.domain.usecase.account.LogoutUseCase
import com.example.restaurantapp.domain.usecase.account.UpdateMeUseCase
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
    private val getMeUseCase: GetMeUseCase,
    private val updateMeUseCase: UpdateMeUseCase,
    private val checkAuthUseCase: CheckAuthUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _message = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val message: SharedFlow<String> = _message.asSharedFlow()

    private val _updateSuccess = MutableSharedFlow<Unit>()
    val updateSuccess: SharedFlow<Unit> = _updateSuccess.asSharedFlow()

    fun getMe() {
        viewModelScope.launch {
            getMeUseCase()
                .onSuccess { user ->
                    _user.value = user
                }.onFailure { error ->
                    _message.emit(error.message ?: "Не удалось загрузить профиль")
                }
        }
    }

    fun updateMe(name: String, surname: String, email: String?) {
        viewModelScope.launch {
            updateMeUseCase(name, surname, email)
                .onSuccess { user ->
                    _user.value = user
                    _updateSuccess.emit(Unit)
                }.onFailure { error ->
                    _message.emit(error.message ?: "Не удалось обновить профиль")
                }
        }
    }

    fun checkAuth() = checkAuthUseCase()

    fun clearSession() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}
