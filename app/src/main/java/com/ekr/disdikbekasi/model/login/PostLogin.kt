package com.ekr.disdikbekasi.model.login

import com.google.gson.annotations.SerializedName

data class PostLogin(
     @SerializedName("nik") val nik : String,
     @SerializedName("password") val password : String
)
