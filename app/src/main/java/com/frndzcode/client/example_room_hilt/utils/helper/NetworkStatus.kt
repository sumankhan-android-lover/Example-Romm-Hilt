package com.frndzcode.client.example_room_hilt.utils.helper

sealed class NetworkStatus{
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}
