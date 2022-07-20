package com.gym.network

import com.gym.model.TaiKhoanModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
interface TaiKhoanService {
    @GET("taikhoan")
    suspend fun getDSTaiKhoan(): Response<List<TaiKhoanModel>>

    @GET("taikhoan/dstk")
    suspend fun getDSTaiKhoanTheoQuyen(@Query("maQuyen") maQuyen: String): Response<List<TaiKhoanModel>>

    @GET("taikhoan/tk")
    suspend fun getTaiKhoan(@Query("maTK") maTK : String) : Response<TaiKhoanModel>

    @POST("taikhoan")
    suspend fun insertTaiKhoan(@Body taiKhoanModel: TaiKhoanModel): Response<TaiKhoanModel>

    @PUT("taikhoan")
    suspend fun updateTaiKhoan(@Body taiKhoanModel: TaiKhoanModel): Response<TaiKhoanModel>

    @DELETE("taikhoan/{maTK}")
    suspend fun deleteTaiKhoan(@Path("maTK") maTK: String)
}