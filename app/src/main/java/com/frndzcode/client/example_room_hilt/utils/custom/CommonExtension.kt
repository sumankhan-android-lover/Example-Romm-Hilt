package com.frndzcode.client.example_room_hilt.utils.custom

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.ConnectivityManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.frndzcode.client.example_room_hilt.BuildConfig
import com.google.android.material.snackbar.Snackbar
import com.frndzcode.client.example_room_hilt.data.network.Resource
import com.frndzcode.client.example_room_hilt.utils.helper.SingleClickListener
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Hides the soft input keyboard from the screen
 */

fun Context.hideSoftKeyboard(activity: Activity) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
}

/**
 * Visibility modifiers and check functions
 */

fun View.isVisible(): Boolean = this.visibility == View.VISIBLE

fun View.isGone(): Boolean = this.visibility == View.GONE

fun View.isInvisible(): Boolean = this.visibility == View.INVISIBLE

fun View.makeVisible() {
    visibility = View.VISIBLE
}
fun View.makeGone() {
    visibility = View.GONE
}
fun View.makeInvisible() {
    visibility = View.INVISIBLE
}



fun String.showShortToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.showLongToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

///**
// * Shows the Snackbar inside an Activity or Fragment
// *
// * @param messageRes Text to be shown inside the Snackbar
// * @param length Duration of the Snackbar
// * @param f Action of the Snackbar
// */
//fun View.showSnackbar(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
//    val snackBar = Snackbar.make(this, resources.getString(messageRes), length)
//    snackBar.f()
//    snackBar.show()
//}
//
///**
// * Adds action to the Snackbar
// *
// * @param actionRes Action text to be shown inside the Snackbar
// * @param color Color of the action text
// * @param listener Onclick listener for the action
// */
//
//fun Snackbar.action(@StringRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
//    setAction(actionRes, listener)
//    color?.let { setActionTextColor(color) }
//}

/**
 * view click to call single event
 */
fun View.singleClick(klik: (View) -> Unit) {
    this.setOnClickListener(object : SingleClickListener() {
        override fun onSingleClick(v: View?) {
            v?.also {
                klik(it)
            }
        }
    })
}

/**
 * fragment to previous fragment back parse data
 */
fun <T : Any> Fragment.setBackStackData(key: String, data : T, doBack : Boolean = true) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
    if(doBack)
        findNavController().popBackStack()
}

fun <T : Any> Fragment.getBackStackData(key: String, result: (T) -> (Unit)) {
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
        ?.observe(viewLifecycleOwner) {
            result(it)
        }
}



fun Fragment.handleApiErrorForFragment(failure: Resource.Failure, retry: (() -> Unit)? = null) {
    when {
        failure.isNetworkError -> {
            requireView().snackBar(
                "Internet connection error. Please check your network connection.",
                retry
            )
        }
        else -> {
            failure.errorBody.toString().showShortToast(requireContext())
        }
    }
}

fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    if (action != null) {
        snackBar.setAction("Retry") {
            action()
        }
        snackBar.duration = Snackbar.LENGTH_INDEFINITE
    } else {
        snackBar.duration = Snackbar.LENGTH_LONG
    }

    action?.let {

    }
    snackBar.show()
}

/**
 * Loads URL into an ImageView using Glide
 *
 * @param url URL to be loaded
 */
fun ImageView.loadFromUrl(url: String, context: Context) {
    Glide.with(context).load(url).into(this)
}

/**
 * Check if the Internet connectivity is available
 */
fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

/**
 * Adds TextWatcher to the EditText
 */
fun EditText.onTextChanged(listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            listener(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

/**
 * Extension method to get a view as bitmap.
 * @sample : val profileImageBitmap = imageViewProfile.getBitmap()
 */
fun View.getBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    draw(canvas)
    canvas.save()
    return bmp
}

/**
 * Extension method to get a facebook hash key
 * @sample : requireActivity().facebookHash()
 */
fun Context.facebookHash() {
    // Add code to print out the key hash
    try {
        val info: PackageInfo = this.packageManager.getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES)
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            showELog("KeyHash: ${Base64.encodeToString(md.digest(), Base64.DEFAULT)}")
        }
    } catch (e: PackageManager.NameNotFoundException) {
        showELog("KeyHash:$e")
    } catch (e: NoSuchAlgorithmException) {
        showELog("KeyHash: $e")
    }
}




