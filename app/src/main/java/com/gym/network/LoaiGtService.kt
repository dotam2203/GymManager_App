package com.gym.network

import com.gym.model.LoaiGtModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
interface LoaiGtService {
    @GET("loaigt/getds")
    suspend fun getDSLoaiGT(): Response<List<LoaiGtModel>>

    @GET("loaigt/get")
    suspend fun getLoaiGT(@Query("idLoaiGT") idLoaiGT: Int): Response<LoaiGtModel>

    @POST("loaigt/post")
    suspend fun insertLoaiGT(@Body loaiGtModel: LoaiGtModel): Response<LoaiGtModel>

    @PUT("loaigt/put")
    suspend fun updateLoaiGT(@Body loaiGtModel: LoaiGtModel): Response<LoaiGtModel>

    @DELETE("loaigt/delete/{idLoaiGT}")
    suspend fun deleteLoaiGT(@Path("idLoaiGT") idLoaiGT: Int)
}