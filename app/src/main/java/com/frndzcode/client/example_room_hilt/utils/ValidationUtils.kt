package com.frndzcode.client.example_room_hilt.utils

import android.content.Context
import com.frndzcode.client.example_room_hilt.utils.custom.emailValid
import com.frndzcode.client.example_room_hilt.utils.custom.phoneValid
import com.frndzcode.client.example_room_hilt.utils.custom.showShortToast
import com.frndzcode.client.example_room_hilt.utils.custom.usernameValid

class ValidationUtils {

    /**
     * @sample use activity and fragment
     * Example :
     *
     * private fun validateForm(): Boolean {
     * if (isValidUsername(this, username = username.text.toString())
     * && isValidEmail(this, email = email.text.toString())
     * && isValidPhone(this, phone = phone.text.toString(),"+91")
     * && isValidPassword(this, password = password.text.toString())) {
     * return true
     * }
     * return false
     * }
     */

    object VALIDATION_OBJECT{

         fun isValidUsername(context: Context, username: String?): Boolean {
            when {
                username.isNullOrEmpty() -> "Please enter User name first.".showShortToast(context)
                !username.usernameValid() -> "Please enter a valid User name.".showShortToast(context)
                else -> return true
            }
            return false
        }

        fun isValidPassword(context: Context, password: String?): Boolean {
            when {
                password.isNullOrEmpty() -> "Please enter Password first.".showShortToast(context)
                password.length < 6 -> "Password length should not be less than 6 characters".showShortToast(context)
                password.length > 30 -> "Password length should not be greater than 30 characters".showShortToast(context)
                else -> return true
            }
            return false
        }

        fun isValidEmail(context: Context, email: String?): Boolean {
            when {
                email.isNullOrEmpty() -> "Please enter Email first.".showShortToast(context)
                !email.emailValid() -> "Please enter a valid Email address.".showShortToast(context)
                else -> return true
            }
            return false
        }

        fun isValidPhone(context: Context, phone: String?, countryCode: String?): Boolean {
            when {
                phone.isNullOrEmpty() -> "Please enter Mobile number first.".showShortToast(context)
                !phone.phoneValid(countryCode) ->  "Please enter a valid Mobile number.".showShortToast(context)
                else -> return true
            }
            return false
        }

    }

}