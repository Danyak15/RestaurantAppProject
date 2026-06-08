package com.example.restaurantapp.di

import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.remote.api.AccountApi
import com.example.restaurantapp.data.remote.api.FavoriteApi
import com.example.restaurantapp.data.remote.api.NewsApi
import com.example.restaurantapp.data.remote.api.ReservationApi
import com.example.restaurantapp.data.remote.api.SyncApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
//    private const val BASE_URL = "http://10.0.2.2:8080"
        private const val BASE_URL = "http://192.168.0.101:8080"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        sessionManager: SessionManager
    ): Interceptor {
        return Interceptor { chain ->
            val token = sessionManager.getToken()

            val request = chain.request().newBuilder().apply {
                if (!token.isNullOrBlank()) {
                    header("Authorization", "Bearer $token")
                }
            }.build()

            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAccountApi(
        retrofit: Retrofit
    ): AccountApi = retrofit.create(AccountApi::class.java)

    @Provides
    @Singleton
    fun provideSyncApi(
        retrofit: Retrofit
    ): SyncApi = retrofit.create(SyncApi::class.java)

    @Provides
    @Singleton
    fun provideFavoriteApi(
        retrofit: Retrofit
    ): FavoriteApi = retrofit.create(FavoriteApi::class.java)

    @Provides
    @Singleton
    fun provideReservationApi(
        retrofit: Retrofit
    ): ReservationApi = retrofit.create(ReservationApi::class.java)

    @Provides
    @Singleton
    fun provideNewsApi(
        retrofit: Retrofit
    ): NewsApi = retrofit.create(NewsApi::class.java)
}