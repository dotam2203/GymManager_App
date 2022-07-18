package com.gym.repository

import com.gym.model.KhachHangModel
import com.gym.network.RetrofitInstance

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  19/07/2022
 */
class KhachHangRepository {
    suspend fun getDSKhachHang(): List<KhachHangModel>? {
        val request = RetrofitInstance.loadApiKhachHang.getDSKhachHang()
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun getDSKhachHangTheoLoaiKH(idLoaiKH: Int): List<KhachHangModel>? {
        val request = RetrofitInstance.loadApiKhachHang.getDSKhachHangTheoLoaiKH(idLoaiKH)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun getKhachHang(maGT: String): KhachHangModel? {
        val request = RetrofitInstance.loadApiKhachHang.getKhachHang(maGT)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }

    suspend fun insertKhachHang(khachHangModel: KhachHangModel): KhachHangModel?{
        val request = RetrofitInstance.loadApiKhachHang.insertKhachHang(khachHangModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun updateKhachHang(khachHangModel: KhachHangModel): KhachHangModel?{
        val request = RetrofitInstance.loadApiKhachHang.updateKhachHang(khachHangModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun deleteKhachHang(maGT: String){
        RetrofitInstance.loadApiKhachHang.deleteKhachHang(maGT)
    }
}