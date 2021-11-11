package com.ekr.disdikbekasi.model.login


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataLogin(
    @SerializedName("agama") val agama: String,
    @SerializedName("alamat") val alamat: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("email") val email: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("nik") val nik: String,
    @SerializedName("role") val role: String,
    @SerializedName("status_karyawan") val statusKaryawan: String,
    @SerializedName("token") val token: String,
    @SerializedName("url_file") val url_file: String?,
    @SerializedName("total_cuti") val totalCuti: Int,
    @SerializedName("upload_foto_id") val uploadFotoId: Int?
): Parcelable