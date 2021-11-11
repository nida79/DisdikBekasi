package com.ekr.disdikbekasi.model.absensi


import com.google.gson.annotations.SerializedName

data class ResponseGetAbsensi(
    @SerializedName("data") val data: List<GetDataAbsensi>?,
    @SerializedName("status") val status: Boolean
)