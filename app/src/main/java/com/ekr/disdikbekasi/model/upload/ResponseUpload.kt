package com.ekr.disdikbekasi.model.upload


import com.google.gson.annotations.SerializedName

data class ResponseUpload(
    @SerializedName("data")
    val dataUpload: DataUpload,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)