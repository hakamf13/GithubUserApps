package com.example.githubuserapps.ui.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapps.data.di.FavoriteRepository
import com.example.githubuserapps.data.local.entity.FavoriteEntity
import com.example.githubuserapps.data.remote.network.ApiConfig
import com.example.githubuserapps.data.remote.response.ItemsItem
import com.example.githubuserapps.data.remote.response.UserResponse
import com.example.githubuserapps.data.token.ConstantToken
import com.example.githubuserapps.data.token.ConstantToken.Companion.TAG_DVM
import com.example.githubuserapps.ext.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val context: Context): ViewModel() {

    private val mfavRepo: FavoriteRepository = FavoriteRepository(context)
    val userFavorite: LiveData<List<FavoriteEntity>> = mfavRepo.getFavoriteUserList()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<UserResponse>()
    val userDetail: LiveData<UserResponse> = _userDetail

    private val _followers = MutableLiveData<List<ItemsItem>?>(null)
    val followers: LiveData<List<ItemsItem>?> = _followers

    private val _following = MutableLiveData<List<ItemsItem>?>(null)
    val following: LiveData<List<ItemsItem>?> = _following

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
                    Log.d(ConstantToken.TAG_FGVM, "onSuccess: ${response.message()}")
                } else {
                    Log.e(ConstantToken.TAG_FGVM, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ConstantToken.TAG_FGVM, "onFailure: ${t.message.toString()}")
            }
        })
    }

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

    fun insertFavoriteUser(id: Int, username: String, avatar: String) {
        val data = FavoriteEntity(id, username, avatar)
        mfavRepo.insertFavoriteUser(data)
        context.showToast("Success Add to Favorite")
    }

    fun deleteFavoriteUser(data: FavoriteEntity) {
        mfavRepo.deleteFavoriteUser(data)
        context.showToast("Success Delete from Favorite")
    }

}