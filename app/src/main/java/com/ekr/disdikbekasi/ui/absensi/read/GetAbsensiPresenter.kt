package com.ekr.disdikbekasi.ui.absensi.read

import com.ekr.disdikbekasi.model.ResponseGlobal
import com.ekr.disdikbekasi.model.absensi.ResponseGetAbsensi
import com.ekr.disdikbekasi.networking.ApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAbsensiPresenter(val view:GetAbsensiContract.View) : GetAbsensiContract.Presenter {

    init {
        view.initListener()
        view.onLoading(false)
    }
    override fun doGetAttendance(
        token: String,
        user_id: Int,
        start_date: String?,
        end_date: String?
    ) {
       view.onLoading(true)
        ApiService.endpoint.getAttendance(token,user_id,start_date,end_date).enqueue(object :
            Callback<ResponseGetAbsensi>{
            override fun onResponse(
                call: Call<ResponseGetAbsensi>,
                response: Response<ResponseGetAbsensi>
            ) {
                view.onLoading(false)
                when {
                    response.isSuccessful -> {
                        val result: ResponseGetAbsensi? = response.body()
                        if (result!!.status) {
                            view.resultGetdata(result)
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

            override fun onFailure(call: Call<ResponseGetAbsensi>, t: Throwable) {
                view.onLoading(false)
                view.showMessage(t.localizedMessage.toString())
            }

        })
    }
}