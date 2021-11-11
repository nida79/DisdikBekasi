package com.ekr.disdikbekasi.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.ekr.disdikbekasi.R

class DialogHelper {
    companion object {

        fun globalLoading(activity: Activity): Dialog {
            val dialog = Dialog(activity)
            dialog.setContentView(R.layout.dialog_global)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager
                    .LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.attributes.windowAnimations = android.R.style.Animation_Dialog
            dialog.setCancelable(true)
            return dialog
        }

        fun dialogFilter(activity: Activity): Dialog {
            val dialog = Dialog(activity)
            dialog.setContentView(R.layout.dialog_filter)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager
                    .LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.attributes.windowAnimations = android.R.style.Animation_Dialog
            dialog.setCancelable(true)
            return dialog
        }

    }
}