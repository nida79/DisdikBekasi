package com.ekr.disdikbekasi.model.upload


import com.google.gson.annotations.SerializedName

data class DataUpload(
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("file_name") val fileName: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("type") val type: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("url_file") val urlFile: String?
)