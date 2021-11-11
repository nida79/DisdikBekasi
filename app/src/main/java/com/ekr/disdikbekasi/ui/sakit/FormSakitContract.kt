package com.ekr.disdikbekasi.ui.sakit

import com.ekr.disdikbekasi.model.ResponseGlobal
import com.ekr.disdikbekasi.model.izin.ResponseGetOtherleave
import com.ekr.disdikbekasi.model.upload.ResponseUpload
import java.io.File

interface FormSakitContract {
    interface Presenter {
        fun doPostOtherLeave(
            token: String,
            startDate: String,
            endDate: String,
            note: String,
            uploadFotoId: Int
        )

        fun doUploadPhoto(
            token: String,
            file: File,
            fileNAme: String,
            type: String
        )
    }

    interface View {
        fun initListener()
        fun onLoading(loading:Boolean)
        fun showMessage(message:String)
        fun resultUpload(responseUpload: ResponseUpload)
        fun resultOtherLeave(responseGlobal: ResponseGlobal)
    }
}