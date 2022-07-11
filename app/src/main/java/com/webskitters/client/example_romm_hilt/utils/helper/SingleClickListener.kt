package com.webskitters.client.example_romm_hilt.utils.helper

import android.os.SystemClock
import android.view.View

abstract class SingleClickListener : View.OnClickListener{
    private val MIN_CLICK_INTERVAL: Long = 1000
    private var mLastClickTime: Long = 0

    abstract fun onSingleClick(v: View?)

    override fun onClick(v: View?) {
        if (mLastClickTime <= 0) {
            mLastClickTime = SystemClock.uptimeMillis()
            onSingleClick(v)
            return
        }

        if (SystemClock.uptimeMillis() - mLastClickTime <= MIN_CLICK_INTERVAL) {
            return
        }

        mLastClickTime = SystemClock.uptimeMillis()

        onSingleClick(v)
    }
}