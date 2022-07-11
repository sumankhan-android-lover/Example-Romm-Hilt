package com.webskitters.client.example_romm_hilt.utils.custom

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.recyclerview.widget.RecyclerView

fun View.expand() {
    val matchParentMeasureSpec =
        View.MeasureSpec.makeMeasureSpec((this.parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    this.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight: Int = this.measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    this.layoutParams.height = 1
    this.visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            this@expand.layoutParams.height =
                if (interpolatedTime == 1f) RecyclerView.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
            this@expand.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Expansion speed of 1dp/ms

    // Expansion speed of 1dp/ms
    a.duration = (targetHeight / this.context.resources.displayMetrics.density).toLong()
    this.startAnimation(a)

}

fun View.collapse() {
    val initialHeight: Int = this.measuredHeight

    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                this@collapse.visibility = View.GONE
            } else {
                this@collapse.layoutParams.height =
                    initialHeight - (initialHeight * interpolatedTime).toInt()
                this@collapse.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Collapse speed of 1dp/ms

    // Collapse speed of 1dp/ms
    a.duration = (initialHeight / this.context.resources.displayMetrics.density).toLong()
    this.startAnimation(a)
}