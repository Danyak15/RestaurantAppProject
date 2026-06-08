package com.example.restaurantapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.restaurantapp.data.local.dao.CategoryDao
import com.example.restaurantapp.data.local.dao.DishDao
import com.example.restaurantapp.data.local.dao.FavoriteDishDao
import com.example.restaurantapp.data.local.dao.FavoriteSyncDao
import com.example.restaurantapp.data.local.dao.RestaurantDao
import com.example.restaurantapp.data.local.dao.UserDao
import com.example.restaurantapp.data.local.entity.CategoryEntity
import com.example.restaurantapp.data.local.entity.DishEntity
import com.example.restaurantapp.data.local.entity.FavoriteDishEntity
import com.example.restaurantapp.data.local.entity.FavoriteSyncEntity
import com.example.restaurantapp.data.local.entity.RestaurantHoursEntity
import com.example.restaurantapp.data.local.entity.RestaurantEntity
import com.example.restaurantapp.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        RestaurantEntity::class,
        CategoryEntity::class,
        DishEntity::class,
        FavoriteDishEntity::class,
        FavoriteSyncEntity::class,
        RestaurantHoursEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun restaurantDao(): RestaurantDao
    abstract fun categoryDao(): CategoryDao
    abstract fun dishDao(): DishDao
    abstract fun favoriteDishDao(): FavoriteDishDao
    abstract fun favoriteSyncDao(): FavoriteSyncDao
}
