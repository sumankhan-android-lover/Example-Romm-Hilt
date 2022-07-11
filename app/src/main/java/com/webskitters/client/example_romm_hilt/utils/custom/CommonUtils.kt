package com.webskitters.client.example_romm_hilt.utils.custom

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.webskitters.client.example_romm_hilt.data.network.Resource
import com.webskitters.client.example_romm_hilt.utils.helper.SingleClickListener

fun Context.hideSoftKeyboard(activity: Activity) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
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

fun String.showShortToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.showLongToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

fun View.singleClick(klik: (View) -> Unit) {
    this.setOnClickListener(object : SingleClickListener() {
        override fun onSingleClick(v: View?) {
            v?.also {
                klik(it)
            }
        }
    })
}

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



