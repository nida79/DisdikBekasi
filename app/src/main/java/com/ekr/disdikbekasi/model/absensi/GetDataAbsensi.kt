package com.ekr.disdikbekasi.model.absensi


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetDataAbsensi(
    @SerializedName("date") val date: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("in") val inX: String?,
    @SerializedName("lat") val lat: String?,
    @SerializedName("lon") val lon: String?,
    @SerializedName("note") val note: String?,
    @SerializedName("out") val out: String?,
    @SerializedName("user") val absensiUser: AbsensiUser?,
    @SerializedName("user_id") val userId: Int?
): Parcelable