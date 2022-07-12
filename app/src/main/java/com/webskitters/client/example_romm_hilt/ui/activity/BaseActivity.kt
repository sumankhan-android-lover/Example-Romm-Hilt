package com.webskitters.client.example_romm_hilt.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webskitters.client.example_romm_hilt.R

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}