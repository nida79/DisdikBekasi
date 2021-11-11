package com.ekr.disdikbekasi.model.izin


import com.google.gson.annotations.SerializedName

data class UserOtherLeave(
    @SerializedName("agama")
    val agama: String?,
    @SerializedName("alamat")
    val alamat: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nik")
    val nik: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("status_karyawan")
    val statusKaryawan: String?,
    @SerializedName("total_cuti")
    val totalCuti: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("upload_foto_id")
    val uploadFotoId: Int?
)