package com.example.restaurantapp.data.local.auth

import android.content.Context
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveCredentials(email: String, password: String) {
        prefs.edit {
            putString("email", email)
            putString("password", password)
        }
    }

    fun getEmail(): String? = prefs.getString("email", null)

    fun getPassword(): String? = prefs.getString("password", null)

    fun isAuthorized(): Boolean {
        return !getEmail().isNullOrBlank() && !getPassword().isNullOrBlank()
    }

    fun clearSession() {
        prefs.edit { clear() }
    }
}