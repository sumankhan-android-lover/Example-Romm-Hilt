package com.webskitters.client.example_romm_hilt.data.repository

import com.webskitters.client.example_romm_hilt.data.network.ApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
) : BaseRepository(){

}