package com.frndzcode.client.example_room_hilt.data.repository

import com.frndzcode.client.example_room_hilt.data.network.ApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
) : BaseRepository(){

}