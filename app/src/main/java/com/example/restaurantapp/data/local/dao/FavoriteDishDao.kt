package com.example.restaurantapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.restaurantapp.data.local.entity.FavoriteDishEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(favoriteDish: FavoriteDishEntity)

    @Query("DELETE FROM favorite_dishes WHERE userId = :userId AND dishId = :dishId")
    suspend fun removeFromFavorites(userId: Long, dishId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_dishes WHERE userId = :userId AND dishId = :dishId)")
    fun observeIsFavorite(userId: Long, dishId: Long): Flow<Boolean>

    @Query("SELECT dishId FROM favorite_dishes WHERE userId = :userId")
    fun observeFavoriteDishIds(userId: Long): Flow<List<Long>>

    @Query("DELETE FROM favorite_dishes WHERE userId = :userId")
    suspend fun clearFavorites(userId: Long)

    @Transaction
    suspend fun updateFavoriteDishes(userId: Long, favoriteIds: List<Long>) {
        clearFavorites(userId)

        favoriteIds.forEach { id ->
            addToFavorites(
                FavoriteDishEntity(
                    userId = userId,
                    dishId = id
                )
            )
        }
    }
}