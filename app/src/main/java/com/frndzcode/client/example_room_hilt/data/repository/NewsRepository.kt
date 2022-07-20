package com.frndzcode.client.example_room_hilt.data.repository

import com.frndzcode.client.example_room_hilt.data.network.ApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
) : BaseRepository(){

    suspend fun getBreakingNews() = safeApiCall {
        apiService.getBreakingNews()
    }

//    @GET("v2/top-headlines")
//    suspend fun getBreakingNews(
//        @Query("country") countryCode: String = "tr",
//        @Query("page") pageNumber: Int = 1,
//        @Query("apiKey") apiKey: String = API_KEY
//    ): Response<NewsResponse>
}