package com.ekr.disdikbekasi.model.cuti


import com.google.gson.annotations.SerializedName

data class ResponseGetCuti(
    @SerializedName("data") val data: List<DataCuti>?,
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)