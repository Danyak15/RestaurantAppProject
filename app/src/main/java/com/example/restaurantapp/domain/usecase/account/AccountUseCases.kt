package com.example.restaurantapp.domain.usecase.account

import com.example.restaurantapp.data.remote.dto.response.LoginResponse
import com.example.restaurantapp.domain.model.User
import com.example.restaurantapp.domain.repository.AccountRepository
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(
        name: String,
        surname: String,
        phone: String,
        password: String
    ): Result<LoginResponse> {
        if (name.isBlank() || surname.isBlank() || phone.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Заполни пустые поля"))
        }

        val normalizedPhone = normalizePhone(phone)
            ?: return Result.failure(IllegalArgumentException("Неверный номер"))

        return accountRepository.register(name, surname, normalizedPhone, password)
            .fold(
                onSuccess = { accountRepository.login(normalizedPhone, password) },
                onFailure = { Result.failure(it) }
            )
    }
}

class LoginUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(phone: String, password: String): Result<LoginResponse> {
        if (phone.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Заполни пустые поля"))
        }

        val normalizedPhone = normalizePhone(phone)
            ?: return Result.failure(IllegalArgumentException("Неверный номер"))

        return accountRepository.login(normalizedPhone, password)
    }
}

class GetMeUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Result<User> = accountRepository.getMe()
}

class UpdateMeUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(
        name: String,
        surname: String,
        email: String?
    ): Result<User> {
        if (name.isBlank() || surname.isBlank()) {
            return Result.failure(IllegalArgumentException("Заполните пустые поля"))
        }

        return accountRepository.updateMe(name, surname, email)
    }
}

class CheckAuthUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke() = accountRepository.checkAuth()
}

class LogoutUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val favoriteDishRepository: FavoriteDishRepository
) {
    suspend operator fun invoke() {
        favoriteDishRepository.clearFavorites()
        accountRepository.clearSession()
    }
}

private fun normalizePhone(phone: String): String? {
    val digits = phone.filter { it.isDigit() }

    return if (digits.length == 10) {
        "+7$digits"
    } else {
        null
    }
}
