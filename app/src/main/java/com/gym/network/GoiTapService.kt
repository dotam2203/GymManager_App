package com.gym.network

import com.gym.model.GoiTapModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
interface GoiTapService {
    @GET("goitap")
    suspend fun getDSGoiTap(): Response<List<GoiTapModel>>

    @GET("goitap/dsgt")
    suspend fun getDSGoiTapTheoLoaiGT(@Query("idLoaiGT") idLoaiGT: Int): Response<List<GoiTapModel>>

    @GET("goitap/gt")
    suspend fun getGoiTap(@Query("maGT") maGT : String) : Response<GoiTapModel>

    @POST("goitap")
    suspend fun insertGoiTap(@Body loaiGtModel: GoiTapModel): Response<GoiTapModel>

    @PUT("goitap")
    suspend fun updateGoiTap(@Body loaiGtModel: GoiTapModel): Response<GoiTapModel>

    @DELETE("goitap/{maGT}")
    suspend fun deleteGoiTap(@Path("maGT") maGT: String): Response<Any>
}