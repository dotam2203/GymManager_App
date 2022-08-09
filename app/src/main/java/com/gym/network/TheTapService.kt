package com.gym.network

import com.gym.model.TheTapModel
import retrofit2.Response
import retrofit2.http.*

interface TheTapService {
    @GET("thetap/getds")
    suspend fun getDSTheTap(): Response<List<TheTapModel>>

    @GET("thetap/getkh")
    suspend fun getDSTheTapTheoKH(@Query("maKH") maKH: String): Response<List<TheTapModel>>

    @GET("thetap/get")
    suspend fun getTheTap(@Query("maKH") maKH: String): Response<TheTapModel>

    @POST("thetap/post")
    suspend fun insertTheTap(@Body theTapModel: TheTapModel): Response<TheTapModel>

    @PUT("thetap/put")
    suspend fun updateTheTap(@Body theTapModel: TheTapModel): Response<TheTapModel>

    @DELETE("thetap/delete/{maThe}")
    suspend fun deleteTheTap(@Path("maThe") maThe: String)
}