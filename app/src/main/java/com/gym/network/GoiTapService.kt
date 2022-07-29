package com.gym.network

import com.gym.model.GoiTapModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
interface GoiTapService {
    @GET("goitap/get")
    suspend fun getDSGoiTap(): Response<List<GoiTapModel>>

    @GET("goitap/getds")
    suspend fun getDSGoiTapTheoLoaiGT(@Query("idLoaiGT") idLoaiGT: Int): Response<List<GoiTapModel>>

    @GET("goitap/getgt")
    suspend fun getGoiTap(@Query("maGT") maGT : String) : Response<GoiTapModel>

    @POST("goitap/post")
    suspend fun insertGoiTap(@Body loaiGtModel: GoiTapModel): Response<GoiTapModel>

    @PUT("goitap/put")
    suspend fun updateGoiTap(@Body loaiGtModel: GoiTapModel): Response<GoiTapModel>

    @DELETE("goitap/delete/{maGT}")
    suspend fun deleteGoiTap(@Path("maGT") maGT: String): Response<Any>
}