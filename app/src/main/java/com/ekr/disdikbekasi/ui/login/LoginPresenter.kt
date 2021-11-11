package com.ekr.disdikbekasi.ui.login

import com.ekr.disdikbekasi.model.ResponseGlobal
import com.ekr.disdikbekasi.model.login.DataLogin
import com.ekr.disdikbekasi.model.login.PostLogin
import com.ekr.disdikbekasi.model.login.ResponseLogin
import com.ekr.disdikbekasi.networking.ApiService
import com.ekr.disdikbekasi.utils.SessionManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {
    init {
        view.initListener()
        view.onLoading(false)

    }

    override fun doLogin(postLogin: PostLogin) {
        view.onLoading(true)
        ApiService.endpoint.doLogin(postLogin).enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                view.onLoading(false)
                when {
                    response.isSuccessful -> {
                        val responseLogin: ResponseLogin? = response.body()
                        if (responseLogin!!.status) {
                            responseLogin.message.let { view.showMessage(it) }
                            view.onResult(responseLogin)
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

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                view.onLoading(false)
                view.showMessage(t.localizedMessage)
//                view.showMessage("Terjadi Kesalahan, Silahkan Coba Kembali")

            }
        })
    }

    override fun setSessions(sessionManager: SessionManager, dataLogin: DataLogin) {
        sessionManager.prefIsLogin  = true
        sessionManager.prefFullname = dataLogin.name
        sessionManager.prefToken    = "Bearer " + dataLogin.token
        sessionManager.prefAgama    = dataLogin.agama
        sessionManager.prefAlamat   = dataLogin.alamat
        sessionManager.prefID       = dataLogin.id.toString()
        sessionManager.prefRole     = dataLogin.role
        sessionManager.prefStatusK  = dataLogin.statusKaryawan
        sessionManager.prefEmail    = dataLogin.email
        sessionManager.prefCuti     = dataLogin.totalCuti.toString()
        sessionManager.prefNIP      = dataLogin.nik
        sessionManager.prefFoto     = dataLogin.url_file.toString()

    }
}