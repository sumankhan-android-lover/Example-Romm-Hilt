package com.frndzcode.client.example_room_hilt.ui.activity

import android.os.Bundle
import com.frndzcode.client.example_room_hilt.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}