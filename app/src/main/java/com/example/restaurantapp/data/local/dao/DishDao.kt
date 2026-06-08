package com.example.restaurantapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantapp.data.local.entity.DishEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DishEntity>)

    @Query("SELECT * FROM dishes WHERE id = :id LIMIT 1")
    suspend fun getDishById(id: Long): DishEntity?

    @Query("SELECT * FROM dishes WHERE categoryId = :categoryId")
    suspend fun getDishesByCategoryId(categoryId: Long): List<DishEntity>

    @Query("SELECT * FROM dishes WHERE id IN (:ids)")
    fun observeDishesByIds(ids: List<Long>): Flow<List<DishEntity>>

    @Query("SELECT * FROM dishes WHERE restaurantId = :restaurantId")
    suspend fun getDishesByRestaurantId(restaurantId: Long): List<DishEntity>
}
