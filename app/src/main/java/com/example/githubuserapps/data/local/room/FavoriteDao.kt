package com.example.githubuserapps.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapps.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite ORDER BY username ASC")
    fun getFavorite(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity)

}