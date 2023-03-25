package com.example.githubuserapps.data.token

import com.example.githubuserapps.R

class ConstantToken {

    companion object {
        const val URL = "https://api.github.com/"
        const val SPLASH_LOADING_TIME: Int = 2000
        const val USERNAME = "username"
        const val TAG_MVM = "MainViewModel"
        const val TAG_DVM = "DetailViewModel"
        const val TAG_FGVM = "FollowingViewModel"
        const val TAG_FSVM = "FollowersViewModel"
        const val USER_KEY = "user_key"
        const val FAV_KEY = "fav_key"
        val TAB_FOLLOWS = intArrayOf(
            R.string.title_tabs_followers,
            R.string.title_tabs_following
        )
    }

}