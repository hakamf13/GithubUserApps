package com.example.githubuserapps.ui.follows.followers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapps.data.remote.network.ApiConfig
import com.example.githubuserapps.data.remote.response.ItemsItem
import com.example.githubuserapps.data.token.ConstantToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  FollowersViewModel(private val context: Context): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers


    fun getUserFollowers(context: Context, followersData: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServce(context).getFollowersData(followersData)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                    Log.d(ConstantToken.TAG_FSVM, "onSuccess: ${response.message()}")
                } else {
                    Log.e(ConstantToken.TAG_FSVM, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(ConstantToken.TAG_FSVM, "onFailure: ${t.message.toString()}")
            }
        })
    }

}