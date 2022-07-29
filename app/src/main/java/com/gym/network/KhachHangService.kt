package com.gym.network

import com.gym.model.KhachHangModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  10/07/2022
 */
interface KhachHangService {
    @GET("khachhang/get")
    suspend fun getDSKhachHang(): Response<List<KhachHangModel>>

    @GET("khachhang/getds")
    suspend fun getDSKhachHangTheoLoaiKH(@Query("idLoaiKH") idLoaiKH: Int): Response<List<KhachHangModel>>

    @GET("khachhang/getkh")
    suspend fun getKhachHang(@Query("maKH") maKH: String): Response<KhachHangModel>

    @POST("khachhang/post")
    suspend fun insertKhachHang(@Body loaiGtModel: KhachHangModel): Response<KhachHangModel>

    @PUT("khachhang/put")
    suspend fun updateKhachHang(@Body loaiGtModel: KhachHangModel): Response<KhachHangModel>

    @DELETE("khachhang/delete/{maKH}")
    suspend fun deleteKhachHang(@Path("maKH") maKH: String)


}