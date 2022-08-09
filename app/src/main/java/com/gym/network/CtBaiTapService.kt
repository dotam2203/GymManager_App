package com.gym.network

import com.gym.model.CtBaiTapModel
import retrofit2.Response
import retrofit2.http.*

interface CtBaiTapService {
    @GET("ctbt/getds")
    suspend fun getDSCtBaiTap(): Response<List<CtBaiTapModel>>

    @GET("ctbt/getgt")
    suspend fun getDSCtBaiTapTheoGT(@Query("maGT") maGT: String): Response<List<CtBaiTapModel>>

    @GET("ctbt/getbt")
    suspend fun getDSCtBaiTapTheoBT(@Query("idBT") idBT: Int): Response<List<CtBaiTapModel>>

    @GET("ctbt/get")
    suspend fun getCtBaiTap(@Query("idCtBaiTap") idCtBaiTap: Int): Response<CtBaiTapModel>

    @POST("ctbt/post")
    suspend fun insertCtBaiTap(@Body ctBaiTapModel: CtBaiTapModel): Response<CtBaiTapModel>

    @PUT("ctbt/put")
    suspend fun updateCtBaiTap(@Body ctBaiTapModel: CtBaiTapModel): Response<CtBaiTapModel>

    @DELETE("ctbt/delete/{idCTBT}")
    suspend fun deleteCtBaiTap(@Path("idCTBT") idCTBT: Int)
}