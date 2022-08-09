package com.gym.network

import com.gym.model.CtKhuyenMaiModel
import retrofit2.Response
import retrofit2.http.*

interface CtKhuyenMaiService {
    @GET("ctkm/getds")
    suspend fun getDSCtKhuyenMai(): Response<List<CtKhuyenMaiModel>>

    @GET("ctkm/getgt")
    suspend fun getDSCtKhuyenMaiTheoGT(@Query("maGT") maGT: String): Response<List<CtKhuyenMaiModel>>

    @GET("ctkm/getkm")
    suspend fun getDSCtKhuyenMaiTheoKM(@Query("idKM") idKM: Int): Response<List<CtKhuyenMaiModel>>

    @GET("ctkm/get")
    suspend fun getCtKhuyenMai(@Query("idCtKhuyenMai") idCtKhuyenMai: Int): Response<CtKhuyenMaiModel>

    @POST("ctkm/post")
    suspend fun insertCtKhuyenMai(@Body ctKhuyenMaiModel: CtKhuyenMaiModel): Response<CtKhuyenMaiModel>

    @PUT("ctkm/put")
    suspend fun updateCtKhuyenMai(@Body ctKhuyenMaiModel: CtKhuyenMaiModel): Response<CtKhuyenMaiModel>

    @DELETE("ctkm/delete/{idCTKM}")
    suspend fun deleteCtKhuyenMai(@Path("idCTKM") idCTKM: Int)
}