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

    @Query("SELECT * FROM favorite_sync_queue WHERE userId = :userId")
    suspend fun getTasks(userId: Long): List<FavoriteSyncEntity>

    @Query("SELECT * FROM favorite_sync_queue WHERE userId = :userId AND dishId = :dishId")
    suspend fun getTask(userId: Long, dishId: Int): FavoriteSyncEntity?

    @Query("DELETE FROM favorite_sync_queue WHERE userId = :userId AND dishId = :dishId")
    suspend fun removeTask(userId: Long, dishId: Int)

    @Query("DELETE FROM favorite_sync_queue WHERE userId = :userId")
    suspend fun clearTasks(userId: Long)
}