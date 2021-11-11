package com.ekr.disdikbekasi.ui.absensi.create

import com.ekr.disdikbekasi.model.ResponseGlobal
import com.ekr.disdikbekasi.model.absensi.PostAbsensiIn
import com.ekr.disdikbekasi.model.absensi.PostAbsensiOut

interface PostAbsenContract {

    interface Presenter {

        fun doAttendanceIn(token: String, dataIn: PostAbsensiIn)
        fun doAttendanceOut(token: String,dataOut: PostAbsensiOut)
        fun doValidateQR(token: String,tokenQR: String)
    }

    interface View {

        fun initListener()
        fun onLoading(loading: Boolean)
        fun showMessage(message: String)
        fun onResultValidate(responseGlobal: ResponseGlobal)
        fun onResult(responseGlobal: ResponseGlobal)
    }

}