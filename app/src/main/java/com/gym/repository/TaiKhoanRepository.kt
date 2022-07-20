package com.gym.repository

import com.gym.model.TaiKhoanModel
import com.gym.network.RetrofitInstance

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
class TaiKhoanRepository {
    suspend fun getDSTaiKhoan(): List<TaiKhoanModel>? {
        val request = RetrofitInstance.loadApiTaiKhoan.getDSTaiKhoan()
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun getDSTaiKhoanTheoQuyen(maQuyen: String): List<TaiKhoanModel>? {
        val request = RetrofitInstance.loadApiTaiKhoan.getDSTaiKhoanTheoQuyen(maQuyen)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun getTaiKhoan(maTK: String): TaiKhoanModel? {
        val request = RetrofitInstance.loadApiTaiKhoan.getTaiKhoan(maTK)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }

    suspend fun insertTaiKhoan(goiTapModel: TaiKhoanModel): TaiKhoanModel?{
        val request = RetrofitInstance.loadApiTaiKhoan.insertTaiKhoan(goiTapModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun updateTaiKhoan(goiTapModel: TaiKhoanModel): TaiKhoanModel?{
        val request = RetrofitInstance.loadApiTaiKhoan.updateTaiKhoan(goiTapModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun deleteTaiKhoan(maTK: String){
        RetrofitInstance.loadApiTaiKhoan.deleteTaiKhoan(maTK)
    }
}