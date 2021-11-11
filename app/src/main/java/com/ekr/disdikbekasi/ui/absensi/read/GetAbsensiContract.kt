package com.ekr.disdikbekasi.ui.absensi.read

import android.widget.EditText
import com.ekr.disdikbekasi.model.absensi.ResponseGetAbsensi
import com.google.android.material.textfield.TextInputEditText

interface GetAbsensiContract {

    interface Presenter {
        fun doGetAttendance(token:String,user_id:Int,start_date:String?,end_date:String?)
    }

    interface View {
        fun initListener()
        fun onLoading(loading:Boolean)
        fun showMessage(message:String)
        fun resultGetdata(responseGetAbsensi: ResponseGetAbsensi)
    }
}