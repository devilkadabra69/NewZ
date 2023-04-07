package com.example.newz.Retrofit

import com.example.newz.ModelClass.Root_Model_Class_Response
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Retrofit_api {

    //
    @GET("v2/top-headlines")
    suspend fun getallbreakingnews(
        @Query("apiKey")apiKey:String,
        @Query("country")country:String="in",
        @Query("page")page:Int=1
        ):Response<Root_Model_Class_Response>



    @GET("v2/everything")
    suspend fun searchfornews(
        @Query("apiKey")apiKey:String,
        @Query("q")q:String,
        @Query("sortBy")sortBy:String="publishedAt",
        @Query("page")page:Int=1
        ):Response<Root_Model_Class_Response>
}