package com.example.restaurantapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantapp.data.local.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE restaurantId = :restaurantId")
    suspend fun getCategoriesByRestaurantId(restaurantId: Long): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CategoryEntity>)
}
