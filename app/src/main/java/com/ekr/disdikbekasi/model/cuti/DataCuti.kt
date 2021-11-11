package com.ekr.disdikbekasi.model.cuti


import com.google.gson.annotations.SerializedName

data class DataCuti(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("jumlah_cuti")
    val jumlahCuti: Int?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("sisa_cuti")
    val sisaCuti: Int?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("updated_by")
    val updatedBy: Int?,
    @SerializedName("user")
    val userDetailCuti: UserDetailCuti?,
    @SerializedName("user_id")
    val userId: Int?
)