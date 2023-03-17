package com.example.githubuserapps.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapps.data.remote.network.ApiConfig
import com.example.githubuserapps.data.remote.response.ItemsItem
import com.example.githubuserapps.data.remote.response.SearchResponse
import com.example.githubuserapps.data.token.ConstantToken.Companion.TAG_MVM
import com.example.githubuserapps.data.token.ConstantToken.Companion.USERNAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _search = MutableLiveData<List<ItemsItem>>()
    val search: LiveData<List<ItemsItem>> = _search

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findItems(context: Context) {
        _isLoading.value = true
        val client = ApiConfig.getApiServce(context).getSearchData(USERNAME)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _search.value = response.body()?.items
                    Log.d(TAG_MVM, "onSuccess: ${response.message()}")
                } else {
                    Log.e(TAG_MVM, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG_MVM, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun searchUser(context: Context, username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServce(context).getSearchData(username)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _search.value = response.body()?.items
                    Log.d(TAG_MVM, "onSuccess: ${response.message()}")
                } else {
                    Log.e(TAG_MVM, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG_MVM, "onFailure: ${t.message.toString()}")
            }
        })
    }
}