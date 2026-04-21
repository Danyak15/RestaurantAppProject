package com.example.restaurantapp.di

import android.content.Context
import androidx.room.Room
import com.example.restaurantapp.data.local.AppDatabase
import com.example.restaurantapp.data.local.DatabaseSeeder
import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.remote.repository.AccountRepositoryImpl
import com.example.restaurantapp.data.repository.CategoriesRepositoryImpl
import com.example.restaurantapp.data.repository.DishesRepositoryImpl
import com.example.restaurantapp.data.repository.RestaurantsRepositoryImpl
import com.example.restaurantapp.data.utils.NetworkHelper
import com.example.restaurantapp.domain.repository.CategoriesRepository
import com.example.restaurantapp.domain.repository.DishesRepository
import com.example.restaurantapp.domain.repository.RestaurantsRepository

class AppContainer(context: Context) {
    private val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "restaurants_app.db"
    ).build()

    val networkHelper = NetworkHelper(context)

    val sessionManager = SessionManager(context)
    val accountRepository = AccountRepositoryImpl(
        userDao = database.userDao(),
        accountApi = NetworkModule.accountApi,
        networkHelper = networkHelper,
        sessionManager = sessionManager
    )

    val restaurantsRepository: RestaurantsRepository =
        RestaurantsRepositoryImpl(database.restaurantDao())

    val categoriesRepository: CategoriesRepository =
        CategoriesRepositoryImpl(database.categoryDao())

    val dishesRepository: DishesRepository =
        DishesRepositoryImpl(database.dishDao())

    val databaseSeeder = DatabaseSeeder(
        restaurantDao = database.restaurantDao(),
        categoryDao = database.categoryDao(),
        dishDao = database.dishDao()
    )
}