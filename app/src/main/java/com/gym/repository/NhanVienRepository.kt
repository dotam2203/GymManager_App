package com.gym.repository

import com.gym.model.NhanVienModel
import com.gym.network.RetrofitInstance

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  20/07/2022
 */
class NhanVienRepository {
    suspend fun getDSNhanVien(): List<NhanVienModel>? {
        val request = RetrofitInstance.loadApiNhanVien.getDSNhanVien()
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun getNhanVien(maNV: String): NhanVienModel?{
        val request = RetrofitInstance.loadApiNhanVien.getNhanVien(maNV)
        if(request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun insertNhanVien(nhanVienModel: NhanVienModel): NhanVienModel?{
        val request = RetrofitInstance.loadApiNhanVien.insertNhanVien(nhanVienModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun updateNhanVien(nhanVienModel: NhanVienModel): NhanVienModel?{
        val request = RetrofitInstance.loadApiNhanVien.updateNhanVien(nhanVienModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun deleteNhanVien(maNV: String){
        RetrofitInstance.loadApiNhanVien.deleteNhanVien(maNV)
    }
}