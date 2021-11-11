package com.ekr.disdikbekasi.model.izin


import com.google.gson.annotations.SerializedName

data class DataOtherLeave(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("jumlah")
    val jumlah: Int?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("updated_by")
    val updatedBy: Any?,
    @SerializedName("upload_foto_id")
    val uploadFotoId: Int?,
    @SerializedName("user")
    val userOtherLeave: UserOtherLeave?,
    @SerializedName("user_id")
    val userId: Int?
)