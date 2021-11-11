package com.ekr.disdikbekasi.ui.absensi.create

import com.ekr.disdikbekasi.model.ResponseGlobal
import com.ekr.disdikbekasi.model.absensi.PostAbsensiIn
import com.ekr.disdikbekasi.model.absensi.PostAbsensiOut
import com.ekr.disdikbekasi.networking.ApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostAbsenPresenter(val view: PostAbsenContract.View) : PostAbsenContract.Presenter {

    init {
        view.initListener()
        view.onLoading(false)
    }

    override fun doAttendanceIn(token: String,dataIn: PostAbsensiIn) {
        view.onLoading(true)
        ApiService.endpoint.doAttendanceIn(token,dataIn).enqueue(object :Callback<ResponseGlobal>{
            override fun onResponse(
                call: Call<ResponseGlobal>,
                response: Response<ResponseGlobal>
            ) {
                view.onLoading(false)
                when {
                    response.isSuccessful -> {
                        val responseGlobal: ResponseGlobal? = response.body()
                        if (responseGlobal!!.status) {
                            responseGlobal.message.let { view.showMessage(it) }
                            view.onResult(responseGlobal)
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
                view.showMessage("Terjadi Kesalahan, Silahkan Coba Kembali")
            }
        })
    }

    override fun doAttendanceOut(token: String, dataOut:PostAbsensiOut) {
        view.onLoading(true)
        ApiService.endpoint.doAttendanceOut(token,dataOut).enqueue(object :Callback<ResponseGlobal>{
            override fun onResponse(
                call: Call<ResponseGlobal>,
                response: Response<ResponseGlobal>
            ) {
                view.onLoading(false)
                when {
                    response.isSuccessful -> {
                        val responseGlobal: ResponseGlobal? = response.body()
                        if (responseGlobal!!.status) {
                            responseGlobal.message.let { view.showMessage(it) }
                            view.onResult(responseGlobal)
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
                view.showMessage("Terjadi Kesalahan, Silahkan Coba Kembali")
            }

        })
    }

    override fun doValidateQR(token: String, tokenQR: String) {
        view.onLoading(true)
        ApiService.endpoint.doValidateQR(token,tokenQR).enqueue(object :Callback<ResponseGlobal>{
            override fun onResponse(
                call: Call<ResponseGlobal>,
                response: Response<ResponseGlobal>
            ) {
                view.onLoading(false)
                when {
                    response.isSuccessful -> {
                        val responseGlobal: ResponseGlobal? = response.body()
                        if (responseGlobal!!.status) {
                            responseGlobal.message.let { view.showMessage(it) }
                            view.onResultValidate(responseGlobal)
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
                view.showMessage("Terjadi Kesalahan, Silahkan Coba Kembali")
            }
        })

    }
}