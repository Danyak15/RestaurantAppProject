package com.example.restaurantapp.di

import android.content.Context
import androidx.room.Room
import com.example.restaurantapp.data.local.AppDatabase
import com.example.restaurantapp.data.local.dao.CategoryDao
import com.example.restaurantapp.data.local.dao.DishDao
import com.example.restaurantapp.data.local.dao.FavoriteDishDao
import com.example.restaurantapp.data.local.dao.FavoriteSyncDao
import com.example.restaurantapp.data.local.dao.RestaurantDao
import com.example.restaurantapp.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "restaurants_app.db"
    ).build()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao =
        database.userDao()

    @Provides
    fun provideFavoriteDishDao(database: AppDatabase): FavoriteDishDao =
        database.favoriteDishDao()

    @Provides
    fun provideFavoriteSyncDao(database: AppDatabase): FavoriteSyncDao =
        database.favoriteSyncDao()

    @Provides
    fun provideRestaurantDao(database: AppDatabase): RestaurantDao =
        database.restaurantDao()

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao =
        database.categoryDao()

    @Provides
    fun provideDishDao(database: AppDatabase): DishDao =
        database.dishDao()
}