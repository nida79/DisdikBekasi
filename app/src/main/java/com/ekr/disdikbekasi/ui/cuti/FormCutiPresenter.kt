package com.ekr.disdikbekasi.ui.cuti

import com.ekr.disdikbekasi.model.ResponseGlobal
import com.ekr.disdikbekasi.model.cuti.ResponseCuti
import com.ekr.disdikbekasi.networking.ApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormCutiPresenter(private val view:FormCutiContract.View) : FormCutiContract.Presenter {
    init {
        view.initListener()
        view.onLoading(false)
    }
    override fun doAnnualLeave(token: String, startDate: String, endDate: String, note: String) {
       view.onLoading(true)
        ApiService.endpoint.doAnnualLeave(token,startDate,endDate,note)
            .enqueue(object : Callback<ResponseCuti>{
                override fun onResponse(
                    call: Call<ResponseCuti>,
                    response: Response<ResponseCuti>
                ) {
                    view.onLoading(false)
                    when {
                        response.isSuccessful -> {
                            val result: ResponseCuti? = response.body()
                            if (result!!.status) {
                                result.message.let { view.showMessage(it) }
                                view.onResult(result)
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

                override fun onFailure(call: Call<ResponseCuti>, t: Throwable) {
                    view.onLoading(false)
                    view.showMessage(t.localizedMessage)
                }

            })
    }
}