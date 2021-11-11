package com.ekr.disdikbekasi.model.absensi

import com.google.gson.annotations.SerializedName

data class PostAbsensiOut(
    @SerializedName("date") val date: String,
    @SerializedName("out") val out: String,
)
