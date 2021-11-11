package com.ekr.disdikbekasi.model.cuti


import com.google.gson.annotations.SerializedName

data class ResponseCuti(
    @SerializedName("data") val data: DataCuti,
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)