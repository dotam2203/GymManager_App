package com.gym.network

import com.gym.model.LoaiKhModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  16/07/2022
 */
interface LoaiKhService {
    @GET("loaikh")
    suspend fun getDSLoaiKH(): Response<List<LoaiKhModel>>

    @GET("loaikh/lkh")
    suspend fun getLoaiKH(@Query("idLoaiKH") idLoaiKH: Int): Response<LoaiKhModel>

    @POST("loaikh")
    suspend fun insertLoaiKH(@Body loaiKhModel: LoaiKhModel): Response<LoaiKhModel>

    @PUT("loaikh")
    suspend fun updateLoaiKH(@Body loaiKhModel: LoaiKhModel): Response<LoaiKhModel>

    @DELETE("loaikh/{idLoaiKH}")
    suspend fun deleteLoaiKH(@Path("idLoaiKH") idLoaiKH: Int)

}