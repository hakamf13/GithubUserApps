package com.example.githubuserapps.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapps.ui.detail.DetailViewModel
import com.example.githubuserapps.ui.follows.followers.FollowersViewModel
import com.example.githubuserapps.ui.follows.following.FollowingViewModel

class ViewModelFactory(private val context: Context)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(context) as T
        } else if (modelClass.isAssignableFrom(FollowersViewModel::class.java)) {
            return FollowersViewModel(context) as T
        } else if (modelClass.isAssignableFrom(FollowingViewModel::class.java)) {
            return FollowingViewModel(context) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}