package com.ekr.disdikbekasi.ui.cuti

import com.ekr.disdikbekasi.model.cuti.ResponseCuti

interface FormCutiContract {

    interface Presenter {
        fun doAnnualLeave(token: String, startDate: String, endDate: String, note: String)
    }

    interface View {
        fun initListener()
        fun onLoading(loading: Boolean)
        fun showMessage(message: String)
        fun onResult(responseCuti: ResponseCuti)
    }
}