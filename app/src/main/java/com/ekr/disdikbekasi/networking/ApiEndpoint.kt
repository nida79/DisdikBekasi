package com.ekr.disdikbekasi.networking

import com.ekr.disdikbekasi.model.ResponseGlobal
import com.ekr.disdikbekasi.model.absensi.PostAbsensiIn
import com.ekr.disdikbekasi.model.absensi.PostAbsensiOut
import com.ekr.disdikbekasi.model.absensi.ResponseGetAbsensi
import com.ekr.disdikbekasi.model.cuti.ResponseCuti
import com.ekr.disdikbekasi.model.login.PostLogin
import com.ekr.disdikbekasi.model.login.ResponseLogin
import com.ekr.disdikbekasi.model.upload.ResponseUpload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import java.io.File

interface ApiEndpoint {

    @POST("login")
    fun doLogin(@Body dataLogin: PostLogin): Call<ResponseLogin>

    @GET("attendance")
    fun getAttendance(
        @Header("Authorization") token: String,
        @Query("user_id") user_id: Int,
        @Query("start_date") start_date: String?,
        @Query("end_date") end_date: String?
    ): Call<ResponseGetAbsensi>

    @POST("attendance")
    fun doAttendanceIn(
        @Header("Authorization") token: String,
        @Body postAbsensiIn: PostAbsensiIn
    ): Call<ResponseGlobal>

    @PUT("attendance-out")
    fun doAttendanceOut(
        @Header("Authorization") token: String,
        @Body postAbsensiOut: PostAbsensiOut
    ): Call<ResponseGlobal>

    @FormUrlEncoded
    @POST("qr-validation")
    fun doValidateQR(
        @Header("Authorization") token: String,
        @Field("data") tokenQR: String
    ): Call<ResponseGlobal>

    @FormUrlEncoded
    @POST("annual-leave")
    fun doAnnualLeave(
        @Header("Authorization") token: String,
        @Field("start_date") start_date: String,
        @Field("end_date") end_date: String,
        @Field("note") note: String
    ): Call<ResponseCuti>

    @GET("annual-leave")
    fun getAnnualLeave(
        @Header("Authorization") token: String,
        @Query("start_date") start_date: String?,
        @Query("end_date") end_date: String?
    ): Call<ResponseCuti>

    @FormUrlEncoded
    @POST("other-leave")
    fun doOtherLeave(
        @Header("Authorization") token: String,
        @Field("start_date") start_date: String,
        @Field("end_date") end_date: String,
        @Field("note") note: String,
        @Field("upload_foto_id") upload_foto_id: Int
    ): Call<ResponseGlobal>

    @Multipart
    @POST("upload")
    fun doUploadPhoto(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("file_name") file_name: RequestBody,
        @Part("type") type: RequestBody
    ): Call<ResponseUpload>
}