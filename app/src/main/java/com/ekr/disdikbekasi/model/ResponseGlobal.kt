package com.ekr.disdikbekasi.model

import com.google.gson.annotations.SerializedName

data class ResponseGlobal(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String

)