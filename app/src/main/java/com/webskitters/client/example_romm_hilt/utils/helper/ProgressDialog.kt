package com.webskitters.client.example_romm_hilt.utils.helper

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.TextView
import com.webskitters.client.example_romm_hilt.R

class ProgressDialog {
    companion object {
        @SuppressLint("InflateParams")
        fun progressDialog(context: Context, message: String): Dialog {
            val dialog = Dialog(context, R.style.TransparentDialogProgress)

            val inflate = LayoutInflater.from(context).inflate(R.layout.progressbar_dialog, null)
            dialog.setContentView(inflate)
            val tvMessage = inflate.findViewById<TextView>(R.id.tvMessage)

          tvMessage.text = message
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            return dialog
        }
    }
}