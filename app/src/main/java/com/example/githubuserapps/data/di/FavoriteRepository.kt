package com.example.githubuserapps.data.di

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.githubuserapps.data.local.entity.FavoriteEntity
import com.example.githubuserapps.data.local.room.FavoriteDao
import com.example.githubuserapps.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(context: Context) {

    private var favoriteDao: FavoriteDao
    private var executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(context)
        favoriteDao = db.favoriteDao()
    }

    fun getFavoriteUserList(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getFavorite()
    }

    fun insertFavoriteUser(favoriteEntity: FavoriteEntity) {
        executorService.execute {
            favoriteDao.insertFavorite(favoriteEntity)
        }
    }

    fun deleteFavoriteUser(favoriteEntity: FavoriteEntity) {
        executorService.execute {
            favoriteDao.deleteFavorite(favoriteEntity)
        }
    }

}