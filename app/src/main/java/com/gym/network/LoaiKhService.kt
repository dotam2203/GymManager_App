package com.gym.network

import com.gym.model.LoaiKhModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  16/07/2022
 */
interface LoaiKhService {
    @GET("loaikh/get")
    suspend fun getDSLoaiKH(): Response<List<LoaiKhModel>>

    @GET("loaikh/getlkh")
    suspend fun getLoaiKH(@Query("idLoaiKH") idLoaiKH: Int): Response<LoaiKhModel>

    @POST("loaikh/post")
    suspend fun insertLoaiKH(@Body loaiKhModel: LoaiKhModel): Response<LoaiKhModel>

    @PUT("loaikh/put")
    suspend fun updateLoaiKH(@Body loaiKhModel: LoaiKhModel): Response<LoaiKhModel>

    @DELETE("loaikh/delete/{idLoaiKH}")
    suspend fun deleteLoaiKH(@Path("idLoaiKH") idLoaiKH: Int)

}