package com.ekr.disdikbekasi.model.login


import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("data")
    val dataLoginLogin: DataLogin?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)