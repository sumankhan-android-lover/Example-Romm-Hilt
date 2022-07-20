package com.frndzcode.client.example_room_hilt.data.model.article

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    val articles: ArrayList<Article>,
    val status: String,
    val totalResults: Int
):Parcelable