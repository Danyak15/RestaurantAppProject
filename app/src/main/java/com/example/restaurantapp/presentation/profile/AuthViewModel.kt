package com.example.restaurantapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _authSuccess = MutableSharedFlow<Unit>()
    val authSuccess: SharedFlow<Unit> = _authSuccess.asSharedFlow()

    private val _message = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun login(phone: String, password: String) {
        if (phone.isBlank() || password.isBlank()) {
            _message.tryEmit("Заполни пустые поля")
            return
        }

        val normalizedPhone = getNormalizedPhone(phone) ?: run {
            _message.tryEmit("Неверный номер")
            return
        }

        viewModelScope.launch {
            accountRepository.login(normalizedPhone, password)
               .onSuccess {
                    _authSuccess.emit(Unit)
                }.onFailure { error ->
                    _message.emit(error.message ?: "Ошибка при входе")
                }
        }
    }

    fun register(name: String, surname: String, phone: String, password: String) {
        if (name.isBlank() || surname.isBlank() || phone.isBlank() || password.isBlank()) {
            _message.tryEmit("Заполни пустые поля")
            return
        }

        val normalizedPhone = getNormalizedPhone(phone) ?: run {
            _message.tryEmit("Неверный номер")
            return
        }

        viewModelScope.launch {
            val registerResult = accountRepository.register(name, surname, normalizedPhone, password)

            registerResult.onSuccess {
                val loginResult = accountRepository.login(normalizedPhone, password)

                loginResult.onSuccess {
                    _authSuccess.emit(Unit)
                }.onFailure { error ->
                    _message.emit(error.message ?: "Ошибка при входе после регистрации")
                }
            }.onFailure { error ->
                _message.emit(error.message ?: "Ошибка при регистрации")
            }
        }
    }

    private fun getNormalizedPhone(phone: String): String? {
        val digits = phone.filter { it.isDigit() }

        return if (digits.length != 10) {
            null
        } else {
            "+7$digits"
        }
    }
}