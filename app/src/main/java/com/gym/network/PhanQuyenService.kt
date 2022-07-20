package com.gym.network

import com.gym.model.PhanQuyenModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  20/07/2022
 */
interface PhanQuyenService {
    @GET("quyen")
    suspend fun getDSQuyen(): Response<List<PhanQuyenModel>>

    @GET("quyen/loaiquyen")
    suspend fun getQuyen(@Query("maQuyen") maQuyen: String): Response<PhanQuyenModel>

    @POST("quyen")
    suspend fun insertQuyen(@Body quyenModel: PhanQuyenModel): Response<PhanQuyenModel>

    @PUT("quyen")
    suspend fun updateQuyen(@Body quyenModel: PhanQuyenModel): Response<PhanQuyenModel>

    @DELETE("quyen/{maQuyen}")
    suspend fun deleteQuyen(@Path("maQuyen") maQuyen: String)
}