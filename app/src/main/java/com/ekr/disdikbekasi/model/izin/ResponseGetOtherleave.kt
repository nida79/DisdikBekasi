package com.ekr.disdikbekasi.model.izin


import com.google.gson.annotations.SerializedName

data class ResponseGetOtherleave(
    @SerializedName("data")
    val `data`: List<DataOtherLeave>?,
    @SerializedName("status")
    val status: Boolean?
)