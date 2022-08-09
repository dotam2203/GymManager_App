package com.gym.network

import com.gym.model.BaiTapModel
import retrofit2.Response
import retrofit2.http.*

interface BaiTapService {
    @GET("baitap/getds")
    suspend fun getDSBaiTap(): Response<List<BaiTapModel>>

    @GET("baitap/get")
    suspend fun getBaiTap(@Query("idBT") idBT: Int): Response<BaiTapModel>

    @POST("baitap/post")
    suspend fun insertBaiTap(@Body loaiKhModel: BaiTapModel): Response<BaiTapModel>

    @PUT("baitap/put")
    suspend fun updateBaiTap(@Body loaiKhModel: BaiTapModel): Response<BaiTapModel>

    @DELETE("baitap/delete/{idBT}")
    suspend fun deleteBaiTap(@Path("idBT") idBT: Int)
}