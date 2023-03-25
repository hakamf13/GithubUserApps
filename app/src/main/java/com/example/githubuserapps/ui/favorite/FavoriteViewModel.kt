package com.example.githubuserapps.ui.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapps.data.di.FavoriteRepository
import com.example.githubuserapps.data.local.entity.FavoriteEntity

class FavoriteViewModel(context: Context): ViewModel() {

    private val mFavRepo: FavoriteRepository = FavoriteRepository(context)
    val favUserList: LiveData<List<FavoriteEntity>> = mFavRepo.getFavoriteUserList()

}