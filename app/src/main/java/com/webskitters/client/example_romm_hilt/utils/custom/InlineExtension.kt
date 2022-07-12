package com.webskitters.client.example_romm_hilt.utils.custom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.webskitters.client.example_romm_hilt.R

/**
 * @sample
 * launchAndFinish<DestinationActivity>()
 * for with intent options
 *
 * launchAndFinish<DestinationActivity>(){
 * putString("myKey", myString)
 * }
 **/
internal inline fun <reified T : Activity> Activity.launch(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.apply(block)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

internal inline fun <reified T : Activity> Activity.launchAndFinish(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.apply(block)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    finish()
}

internal inline fun <reified T : Activity> Activity.launchAndFinishAffinity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.apply(block)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    finishAffinity()
}