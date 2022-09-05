package com.gym.network

import com.gym.model.CtTheTapModel
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface CtTheTapService {
    @GET("ctthe/getds")
    suspend fun getDSCtTheTap(): Response<List<CtTheTapModel>>

    @GET("ctthe/sort")
    suspend fun getLocDSCtTheTap(@Query("ngayBD") ngayBD: String, @Query("ngayKT") ngayKT: String): Response<List<CtTheTapModel>>

    @GET("ctthe/getgt")
    suspend fun getDSCtTheTapTheoGT(@Query("maGT") maGT: String): Response<List<CtTheTapModel>>

    @GET("ctthe/gethd")
    suspend fun getDSCtTheTapTheoHD(@Query("maHD") maHD: String): Response<List<CtTheTapModel>>

    @GET("ctthe/getthe")
    suspend fun getCtTheTapTheoThe(@Query("maThe") maThe: String): Response<CtTheTapModel>

    @GET("ctthe/get")
    suspend fun getCtTheTap(@Query("idCTThe") idCTThe: Int): Response<CtTheTapModel>

    @POST("ctthe/post")
    suspend fun insertCtTheTap(@Body ctTheTapModel: CtTheTapModel): Response<CtTheTapModel>

    @PUT("ctthe/put")
    suspend fun updateCtTheTap(@Body ctTheTapModel: CtTheTapModel): Response<CtTheTapModel>

    @DELETE("ctthe/delete/{idCTBT}")
    suspend fun deleteCtTheTap(@Path("idCTBT") idCTBT: Int)
}