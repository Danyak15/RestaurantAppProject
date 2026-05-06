package com.example.restaurantapp.data.local.auth

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit {
            putString("token", token)
        }
    }

    fun getToken(): String? = prefs.getString("token", null)

    fun isAuthorized(): Boolean = !getToken().isNullOrBlank()

    fun clearSession() {
        prefs.edit { clear() }
    }
}