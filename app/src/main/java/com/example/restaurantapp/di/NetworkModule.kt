package com.example.restaurantapp.di

import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.remote.api.AccountApi
import com.example.restaurantapp.data.remote.api.FavoriteApi
import com.example.restaurantapp.data.remote.api.ReservationApi
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule(
    private val sessionManager: SessionManager
) {
    private val authInterceptor = Interceptor { chain ->
        val request = chain.request()
        val email = sessionManager.getEmail()
        val password = sessionManager.getPassword()

        val authenticatedRequest = if (email != null && password != null) {
            val credentials = Credentials.basic(email, password)
            request.newBuilder()
                .header("Authorization", credentials)
                .build()
        } else {
            request
        }

        chain.proceed(authenticatedRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val accountApi: AccountApi = retrofit.create(AccountApi::class.java)
    val favoriteApi: FavoriteApi = retrofit.create(FavoriteApi::class.java)
    val reservationApi: ReservationApi = retrofit.create(ReservationApi::class.java)

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/"
//        private const val BASE_URL = "http://192.168.0.106:8080/"
    }
}