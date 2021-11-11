package com.ekr.disdikbekasi.model.absensi


import com.google.gson.annotations.SerializedName

data class PostAbsensiIn(
    @SerializedName("date") val date: String?,
    @SerializedName("in") val inX: String?,
    @SerializedName("note") val note: String,
    @SerializedName("lat") val lat: String,
    @SerializedName("lon") val lon: String

)