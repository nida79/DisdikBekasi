package com.ekr.disdikbekasi.ui.login

import com.ekr.disdikbekasi.model.login.DataLogin
import com.ekr.disdikbekasi.model.login.PostLogin
import com.ekr.disdikbekasi.model.login.ResponseLogin
import com.ekr.disdikbekasi.utils.SessionManager

interface LoginContract {

    interface Presenter{
        fun doLogin(postLogin: PostLogin)
        fun setSessions(sessionManager: SessionManager, dataLogin: DataLogin)
    }

    interface View{
        fun initListener()
        fun onLoading(loading:Boolean)
        fun onResult(responseLogin: ResponseLogin)
        fun showMessage(message:String)
    }
}