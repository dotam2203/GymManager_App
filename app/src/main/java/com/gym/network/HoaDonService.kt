package com.gym.network

import com.gym.model.HoaDonModel
import retrofit2.Response
import retrofit2.http.*

interface HoaDonService {
    @GET("hoadon/getds")
    suspend fun getDSHoaDon(): Response<List<HoaDonModel>>

    @GET("hoadon/getnv")
    suspend fun getDSHoaDonTheoNV(@Query("maNV") maNV: String): Response<List<HoaDonModel>>
    @GET("hoadon/getthe")
    suspend fun getDSHoaDonTheoThe(@Query("maThe") maThe: String): Response<List<HoaDonModel>>

    @GET("hoadon/get")
    suspend fun getHoaDon(@Query("maHD") maHD: String): Response<HoaDonModel>

    @POST("hoadon/post")
    suspend fun insertHoaDon(@Body loaiGtModel: HoaDonModel): Response<HoaDonModel>

    @PUT("hoadon/put")
    suspend fun updateHoaDon(@Body loaiGtModel: HoaDonModel): Response<HoaDonModel>

    @DELETE("hoadon/delete/{maHD}")
    suspend fun deleteHoaDon(@Path("maHD") maHD: String)
}