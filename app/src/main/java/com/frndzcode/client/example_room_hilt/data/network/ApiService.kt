package com.frndzcode.client.example_room_hilt.data.network

import com.frndzcode.client.example_room_hilt.app.MyConstants.API_CONST.API_KEY
import com.frndzcode.client.example_room_hilt.app.MyConstants.API_CONST.top_headlines
import com.frndzcode.client.example_room_hilt.data.model.article.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(top_headlines)
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "in",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

}