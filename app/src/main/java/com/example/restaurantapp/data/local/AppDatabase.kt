package com.example.restaurantapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.restaurantapp.data.local.dao.CategoryDao
import com.example.restaurantapp.data.local.dao.DishDao
import com.example.restaurantapp.data.local.dao.RestaurantDao
import com.example.restaurantapp.data.local.entity.CategoryEntity
import com.example.restaurantapp.data.local.entity.DishEntity
import com.example.restaurantapp.data.local.entity.RestaurantEntity


@Database(
    entities = [
        RestaurantEntity::class,
        CategoryEntity::class,
        DishEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun categoryDao(): CategoryDao
    abstract fun dishDao(): DishDao
}