package com.example.restaurantapp

import android.app.Application
import com.example.restaurantapp.di.AppContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantApplication : Application() {
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)

        CoroutineScope(Dispatchers.IO).launch {
            appContainer.databaseSeeder.seedIfNeeded()
        }
    }
}