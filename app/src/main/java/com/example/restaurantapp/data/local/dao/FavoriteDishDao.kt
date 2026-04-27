package com.example.restaurantapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantapp.data.local.entity.FavoriteDishEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(favoriteDish: FavoriteDishEntity)

    @Query("DELETE FROM favorite_dishes WHERE userEmail = :userEmail AND dishId = :dishId")
    suspend fun removeFromFavorites(userEmail: String, dishId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_dishes WHERE userEmail = :userEmail AND dishId = :dishId)")
    fun observeIsFavorite(userEmail: String, dishId: Int): Flow<Boolean>

    @Query("SELECT dishId FROM favorite_dishes WHERE userEmail = :userEmail")
    fun observeFavoriteDishIds(userEmail: String): Flow<List<Int>>

    @Query("DELETE FROM favorite_dishes WHERE userEmail = :userEmail")
    suspend fun clearFavorites(userEmail: String)
}