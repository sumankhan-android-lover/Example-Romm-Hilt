package com.frndzcode.client.example_room_hilt.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class DataBindingUtils {


    companion object{
        fun <T : ViewDataBinding?> putContentView(
            @LayoutRes resId: Int,
            inflater: LayoutInflater,
            container: ViewGroup?
        ): T {
            val bind: ViewDataBinding =
                DataBindingUtil.inflate(inflater, resId, container, false)
            return bind as T
        }
    }
}


