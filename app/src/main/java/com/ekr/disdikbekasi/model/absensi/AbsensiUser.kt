package com.ekr.disdikbekasi.model.absensi


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  AbsensiUser(
    @SerializedName("agama") val agama: String?,
    @SerializedName("alamat") val alamat: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("nik") val nik: String?,
    @SerializedName("role") val role: String?,
    @SerializedName("status_karyawan") val statusKaryawan: String?,
    @SerializedName("total_cuti") val totalCuti: Int?,
    @SerializedName("upload_foto_id") val uploadFotoId: String?
): Parcelable