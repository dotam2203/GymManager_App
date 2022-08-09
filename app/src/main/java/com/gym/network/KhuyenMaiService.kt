package com.gym.network

import com.gym.model.KhuyenMaiModel
import retrofit2.Response
import retrofit2.http.*

interface KhuyenMaiService {
    @GET("khuyenmai/getds")
    suspend fun getDSKhuyenMai(): Response<List<KhuyenMaiModel>>

    @GET("khuyenmai/getnv")
    suspend fun getDSKhuyenMaiTheoNV(@Query("maNV") maNV: String): Response<List<KhuyenMaiModel>>

    @GET("khuyenmai/get")
    suspend fun getKhuyenMai(@Query("idKM") idKM: Int): Response<KhuyenMaiModel>

    @POST("khuyenmai/post")
    suspend fun insertKhuyenMai(@Body khuyenMaiModel: KhuyenMaiModel): Response<KhuyenMaiModel>

    @PUT("khuyenmai/put")
    suspend fun updateKhuyenMai(@Body khuyenMaiModel: KhuyenMaiModel): Response<KhuyenMaiModel>

    @DELETE("khuyenmai/delete/{idKM}")
    suspend fun deleteKhuyenMai(@Path("idKM") idKM: Int)
}