package com.example.restaurantapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantapp.data.local.entity.DishEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface DishDao {
    @Query("SELECT * FROM dishes WHERE categoryId = :categoryId")
    fun getDishesByCategoryId(categoryId: Int): Flow<List<DishEntity>>

    @Query("SELECT * FROM dishes WHERE id = :id LIMIT 1")
    suspend fun getDishById(id: Int): DishEntity?

    @Query("UPDATE dishes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DishEntity>)
}