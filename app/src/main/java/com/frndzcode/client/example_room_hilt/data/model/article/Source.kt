package com.frndzcode.client.example_room_hilt.data.model.article

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    val id: String,
    val name: String
):Parcelable