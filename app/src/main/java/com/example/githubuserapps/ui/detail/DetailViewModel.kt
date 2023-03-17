package com.example.githubuserapps.ui.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapps.data.remote.network.ApiConfig
import com.example.githubuserapps.data.remote.response.UserResponse
import com.example.githubuserapps.data.token.ConstantToken.Companion.TAG_DVM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<UserResponse>()
    val userdetail: LiveData<UserResponse> = _userDetail

    fun getUserData(context: Context, userData: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServce(context).getUserData(userData)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                    Log.d(TAG_DVM, "onSuccess: ${response.message()}")
                } else {
                    Log.e(TAG_DVM, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG_DVM, "onFailure: ${t.message.toString()}")
            }
        })
    }

}