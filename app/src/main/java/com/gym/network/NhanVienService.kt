package com.gym.network

import com.gym.model.NhanVienModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
interface NhanVienService {
    @GET("nhanvien")
    suspend fun getDSNhanVien(): Response<List<NhanVienModel>>

    @GET("nhanvien/nv")
    suspend fun getNhanVien(@Query("maNV") maNV : String) : Response<NhanVienModel>

    @POST("nhanvien")
    suspend fun insertNhanVien(@Body nhanVienModel: NhanVienModel): Response<NhanVienModel>

    @PUT("nhanvien")
    suspend fun updateNhanVien(@Body nhanVienModel: NhanVienModel): Response<NhanVienModel>

    @DELETE("nhanvien/{maNV}")
    suspend fun deleteNhanVien(@Path("maNV") maNV: String)
}