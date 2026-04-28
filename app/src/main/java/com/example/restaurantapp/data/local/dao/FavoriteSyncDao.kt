package com.example.restaurantapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantapp.data.local.entity.FavoriteSyncEntity

@Dao
interface FavoriteSyncDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: FavoriteSyncEntity)

    @Query("SELECT * FROM favorite_sync_queue WHERE userEmail = :email")
    suspend fun getTasks(email: String): List<FavoriteSyncEntity>

    @Query("SELECT * FROM favorite_sync_queue WHERE userEmail = :email AND dishId = :dishId")
    suspend fun getTask(email: String, dishId: Int): FavoriteSyncEntity?

    @Query("DELETE FROM favorite_sync_queue WHERE userEmail = :email AND dishId = :dishId")
    suspend fun removeTask(email: String, dishId: Int)

    @Query("DELETE FROM favorite_sync_queue WHERE userEmail = :email")
    suspend fun clearTasks(email: String)
}