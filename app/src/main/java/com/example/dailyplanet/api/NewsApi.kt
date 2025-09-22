package com.example.dailyplanet.api

import com.example.dailyplanet.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    // ... inside interface NewsApi
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("category") // <-- ADD THIS
        category: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>
// ...

    // ... inside interface NewsApi

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>

// ...
}