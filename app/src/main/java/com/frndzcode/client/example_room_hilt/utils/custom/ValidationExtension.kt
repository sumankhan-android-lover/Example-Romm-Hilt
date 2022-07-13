package com.frndzcode.client.example_room_hilt.utils.custom

import android.widget.EditText
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.frndzcode.client.example_room_hilt.app.MyConstants.COMMON_CONST.EMAIL_PATTERN
import com.frndzcode.client.example_room_hilt.app.MyConstants.COMMON_CONST.USERNAME_MATCHES
import java.util.regex.Pattern

fun EditText.getTrimmedText(): String {
    return this.text.toString().trim()
}

/**
 * Email validation
 */
fun String.emailValid(): Boolean = (Pattern.compile(EMAIL_PATTERN).matcher(this).matches() && this.isNotBlank())

/**
 * Username validation
 */
fun String.usernameValid(): Boolean = (Pattern.compile(USERNAME_MATCHES).matcher(this).matches() && this.isNotBlank())

/**
 * Phone authorization by country code
 */
fun String.phoneValid(countryCode:String?):Boolean {
    val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
    try {
        val numberProto = phoneUtil.parse(countryCode + this, null)
        return phoneUtil.isValidNumber(numberProto)
    } catch (e: NumberParseException) {
        System.err.println("NumberParseException was thrown: $e")
    }
    return false
}