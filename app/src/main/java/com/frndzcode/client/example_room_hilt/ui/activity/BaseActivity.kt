package com.frndzcode.client.example_room_hilt.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frndzcode.client.example_room_hilt.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}