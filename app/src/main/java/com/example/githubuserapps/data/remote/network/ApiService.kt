package com.example.githubuserapps.data.remote.network

import com.example.githubuserapps.BuildConfig.KEY
import com.example.githubuserapps.data.remote.response.ItemsItem
import com.example.githubuserapps.data.remote.response.SearchResponse
import com.example.githubuserapps.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: $KEY")
    @GET("search/users")
    fun getSearchData(
        @Query("q") query: String
    ) : Call<SearchResponse>

    @Headers("Authorization: $KEY")
    @GET("users/{username}")
    fun getUserData(
        @Path("username") username: String
    ): Call<UserResponse>

    @Headers("Authorization: $KEY")
    @GET("users/{username}/followers")
    fun getFollowersData(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @Headers("AUthorization: $KEY")
    @GET("users/{username}/following")
    fun getFollowingData(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}
