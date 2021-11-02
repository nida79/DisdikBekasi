package com.ekr.disdikbekasi.networking

import com.ekr.disdikbekasi.model.login.PostLogin
import com.ekr.disdikbekasi.model.login.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiEndpoint {

    @POST("login")
    fun doLogin(@Body dataLogin: PostLogin): Call<ResponseLogin>
}