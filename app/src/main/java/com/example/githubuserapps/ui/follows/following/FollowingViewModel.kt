package com.example.githubuserapps.ui.follows.following

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapps.data.remote.network.ApiConfig
import com.example.githubuserapps.data.remote.response.ItemsItem
import com.example.githubuserapps.data.token.ConstantToken.Companion.TAG_FGVM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel(private val context: Context): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following


    fun getUserFollowing(context: Context, followingData: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServce(context).getFollowingData(followingData)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                    Log.d(TAG_FGVM, "onSuccess: ${response.message()}")
                } else {
                    Log.e(TAG_FGVM, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG_FGVM, "onFailure: ${t.message.toString()}")
            }
        })
    }
}