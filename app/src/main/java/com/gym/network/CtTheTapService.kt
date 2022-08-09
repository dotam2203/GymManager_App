package com.gym.network

import com.gym.model.CtTheTapModel
import retrofit2.Response
import retrofit2.http.*

interface CtTheTapService {
    @GET("ctthe/getds")
    suspend fun getDSCtTheTap(): Response<List<CtTheTapModel>>

    @GET("ctthe/getgt")
    suspend fun getDSCtTheTapTheoGT(@Query("maGT") maGT: String): Response<List<CtTheTapModel>>

    @GET("ctthe/gethd")
    suspend fun getDSCtTheTapTheoHD(@Query("maHD") maHD: String): Response<List<CtTheTapModel>>

    @GET("ctthe/getthe")
    suspend fun getDSCtTheTapTheoThe(@Query("maThe") maThe: String): Response<List<CtTheTapModel>>

    @GET("ctthe/get")
    suspend fun getCtTheTap(@Query("idCtTheTap") idCtTheTap: Int): Response<CtTheTapModel>

    @POST("ctthe/post")
    suspend fun insertCtTheTap(@Body ctTheTapModel: CtTheTapModel): Response<CtTheTapModel>

    @PUT("ctthe/put")
    suspend fun updateCtTheTap(@Body ctTheTapModel: CtTheTapModel): Response<CtTheTapModel>

    @DELETE("ctthe/delete/{idCTBT}")
    suspend fun deleteCtTheTap(@Path("idCTBT") idCTBT: Int)
}