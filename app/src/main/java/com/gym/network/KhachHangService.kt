package com.gym.network

import com.gym.model.KhachHangModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  10/07/2022
 */
interface KhachHangService {
    @GET("khachhang")
    suspend fun getDSKhachHang(): Response<List<KhachHangModel>>

    @GET("khachhang/dskh")
    suspend fun getDSKhachHangTheoLoaiKH(@Query("idLoaiKH") idLoaiKH: Int): Response<List<KhachHangModel>>

    @GET("khachhang/kh")
    suspend fun getKhachHang(@Query("maKH") maKH: String): Response<KhachHangModel>

    @POST("khachhang")
    suspend fun insertKhachHang(@Body loaiGtModel: KhachHangModel): Response<KhachHangModel>

    @PUT("khachhang")
    suspend fun updateKhachHang(@Body loaiGtModel: KhachHangModel): Response<KhachHangModel>

    @DELETE("khachhang/{maKH}")
    suspend fun deleteKhachHang(@Path("maKH") maKH: String)


}