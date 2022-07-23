package com.gym.network

import com.gym.model.GiaGtModel
import retrofit2.Response
import retrofit2.http.*

interface GiaGtService {
    @GET("gia")
    suspend fun getDSGia(): Response<List<GiaGtModel>>

    @GET("gia/gt")
    suspend fun getDSGiaTheoGoiTap(@Query("maGT") maGT: String): Response<List<GiaGtModel>>

    @GET("gia/nv")
    suspend fun getDSGiaTheoNhanVien(@Query("maNV") maNV: String): Response<List<GiaGtModel>>

    @GET("gia/gia")
    suspend fun getGia(@Query("idGia") idGia: Int): Response<GiaGtModel>

    @POST("gia")
    suspend fun insertGia(@Body giaGtModel: GiaGtModel): Response<GiaGtModel>

    @PUT("gia")
    suspend fun updateGia(@Body giaGtModel: GiaGtModel): Response<GiaGtModel>

    @DELETE("gia/{idGia}")
    suspend fun deleteGia(@Path("idGia") idGia: Int)
}