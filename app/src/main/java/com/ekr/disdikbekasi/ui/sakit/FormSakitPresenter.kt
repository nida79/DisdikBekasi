package com.ekr.disdikbekasi.ui.sakit

import android.util.Log
import com.ekr.disdikbekasi.model.ResponseGlobal
import com.ekr.disdikbekasi.model.upload.ResponseUpload
import com.ekr.disdikbekasi.networking.ApiService
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FormSakitPresenter(val view: FormSakitContract.View) : FormSakitContract.Presenter {

    init {
        view.initListener()
        view.onLoading(false)
    }

    override fun doPostOtherLeave(
        token: String,
        startDate: String,
        endDate: String,
        note: String,
        uploadFotoId: Int
    ) {
        view.onLoading(true)
        ApiService.endpoint.doOtherLeave(token, startDate, endDate, note, uploadFotoId)
            .enqueue(object :
                Callback<ResponseGlobal> {
                override fun onResponse(
                    call: Call<ResponseGlobal>,
                    response: Response<ResponseGlobal>
                ) {
                    view.onLoading(false)
                    when {
                        response.isSuccessful -> {
                            val result: ResponseGlobal? = response.body()
                            if (result!!.status) {
                                result.message.let { view.showMessage(it) }
                                view.resultOtherLeave(result)
                            }
                        }
                        response.code() != 200 -> {
                            val responseGlobal: ResponseGlobal = Gson().fromJson(
                                response.errorBody()!!.charStream(),
                                ResponseGlobal::class.java
                            )
                            view.showMessage(responseGlobal.message)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseGlobal>, t: Throwable) {
                    view.onLoading(false)
                    view.showMessage(t.localizedMessage)
                }

            })
    }

    override fun doUploadPhoto(token: String, file: File, fileNAme: String, type: String) {
        view.onLoading(true)
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val multipartBody: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            file.name, requestBody
        )
        val fileName: RequestBody = RequestBody.create(MediaType.parse("text/plain"), fileNAme)
        val fileType : RequestBody = RequestBody.create(MediaType.parse("text/plain"),type)
        ApiService.endpoint.doUploadPhoto(token,multipartBody,fileName,fileType)
            .enqueue(object :Callback<ResponseUpload>{
                override fun onResponse(
                    call: Call<ResponseUpload>,
                    response: Response<ResponseUpload>
                ) {
                    view.onLoading(false)
                    when {
                        response.isSuccessful -> {
                            val result: ResponseUpload? = response.body()
                            if (result!!.status) {
                                result.message.let { view.showMessage(it) }
                                view.resultUpload(result)
                            }
                        }
                        response.code() != 200 -> {
                            val responseGlobal: ResponseGlobal = Gson().fromJson(
                                response.errorBody()!!.charStream(),
                                ResponseGlobal::class.java
                            )
                            view.showMessage(responseGlobal.message)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseUpload>, t: Throwable) {
                    view.onLoading(false)
                    view.showMessage(t.localizedMessage)
                }

            })
    }
}