package com.webskitters.client.example_romm_hilt.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    val id: String,
    val name: String
):Parcelable