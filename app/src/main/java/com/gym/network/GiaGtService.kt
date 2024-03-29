package com.gym.network

import com.gym.model.GiaGtModel
import retrofit2.Response
import retrofit2.http.*

interface GiaGtService {
    @GET("gia/getds")
    suspend fun getDSGia(): Response<List<GiaGtModel>>

    @GET("gia/getgt")
    suspend fun getDSGiaTheoGoiTap(@Query("maGT") maGT: String): Response<List<GiaGtModel>>

    @GET("gia/getnv")
    suspend fun getDSGiaTheoNhanVien(@Query("maNV") maNV: String): Response<List<GiaGtModel>>

    @GET("gia/get")
    suspend fun getGia(@Query("idGia") idGia: Int): Response<GiaGtModel>

    @POST("gia/post")
    suspend fun insertGia(@Body giaGtModel: GiaGtModel): Response<GiaGtModel>

    @PUT("gia/put")
    suspend fun updateGia(@Body giaGtModel: GiaGtModel): Response<GiaGtModel>

    @DELETE("gia/delete/{idGia}")
    suspend fun deleteGia(@Path("idGia") idGia: Int)
}