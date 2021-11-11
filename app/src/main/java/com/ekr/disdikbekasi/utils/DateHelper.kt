package com.ekr.disdikbekasi.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class DateHelper {
    companion object {
        @SuppressLint("SetTextI18n")
        fun chooseDate(activity: Activity, texinput: TextInputEditText) {
            val calendar = Calendar.getInstance()
            val tahun = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(activity, { _, year, monthOfYear, dayOfMonth ->
                val bulan = monthOfYear + 1
                when {
                    bulan <= 9 && dayOfMonth > 9 -> texinput.setText("$year-0$bulan-$dayOfMonth")
                    dayOfMonth <= 9 && bulan <= 9 -> texinput.setText("$year-0$bulan-0$dayOfMonth")
                    bulan <= 9 -> texinput.setText("$year-0$bulan-$dayOfMonth")
                    dayOfMonth <= 9 -> texinput.setText("$year-$bulan-0$dayOfMonth")
                    else -> texinput.setText("$year-$bulan-$dayOfMonth")
                }
            }, tahun, month, day)
            dpd.show()
        }
    }
}