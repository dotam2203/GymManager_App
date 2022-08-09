package com.gym.network

import com.gym.model.TaiKhoanModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
interface TaiKhoanService {
    @GET("taikhoan/getds")
    suspend fun getDSTaiKhoan(): Response<List<TaiKhoanModel>>

    @GET("taikhoan/getquyen")
    suspend fun getDSTaiKhoanTheoQuyen(@Query("maQuyen") maQuyen: String): Response<List<TaiKhoanModel>>

    @GET("taikhoan/get")
    suspend fun getTaiKhoan(@Query("maTK") maTK : String) : Response<TaiKhoanModel>

    @POST("taikhoan/post")
    suspend fun insertTaiKhoan(@Body taiKhoanModel: TaiKhoanModel): Response<TaiKhoanModel>

    @PUT("taikhoan/put")
    suspend fun updateTaiKhoan(@Body taiKhoanModel: TaiKhoanModel): Response<TaiKhoanModel>

    @DELETE("taikhoan/delete/{maTK}")
    suspend fun deleteTaiKhoan(@Path("maTK") maTK: String)
}