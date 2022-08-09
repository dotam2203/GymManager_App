package com.gym.network

import com.gym.model.KhachHangModel
import retrofit2.Response
import retrofit2.http.*


interface KhachHangService {
    @GET("khachhang/getds")
    suspend fun getDSKhachHang(): Response<List<KhachHangModel>>

    @GET("khachhang/getloaikh")
    suspend fun getDSKhachHangTheoLoaiKH(@Query("idLoaiKH") idLoaiKH: Int): Response<List<KhachHangModel>>

    @GET("khachhang/get")
    suspend fun getKhachHang(@Query("maKH") maKH: String): Response<KhachHangModel>

    @POST("khachhang/post")
    suspend fun insertKhachHang(@Body loaiGtModel: KhachHangModel): Response<KhachHangModel>

    @PUT("khachhang/put")
    suspend fun updateKhachHang(@Body loaiGtModel: KhachHangModel): Response<KhachHangModel>

    @DELETE("khachhang/delete/{maKH}")
    suspend fun deleteKhachHang(@Path("maKH") maKH: String)


}