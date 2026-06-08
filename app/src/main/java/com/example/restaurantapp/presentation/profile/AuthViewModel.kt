package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.usecase.account.LoginUseCase
import com.example.restaurantapp.domain.usecase.account.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _authSuccess = MutableSharedFlow<Unit>()
    val authSuccess: SharedFlow<Unit> = _authSuccess.asSharedFlow()

    private val _message = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun login(phone: String, password: String) {
        viewModelScope.launch {
            loginUseCase(phone, password)
                .onSuccess {
                    _authSuccess.emit(Unit)
                }.onFailure { error ->
                    _message.emit(error.message ?: "Ошибка при входе")
                }
        }
    }

    fun register(name: String, surname: String, phone: String, password: String) {
        viewModelScope.launch {
            registerUseCase(name, surname, phone, password)
                .onSuccess {
                    _authSuccess.emit(Unit)
                }.onFailure { error ->
                    _message.emit(error.message ?: "Ошибка при регистрации")
                }
        }
    }
}
