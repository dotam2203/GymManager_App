package com.gym.repository

import com.gym.model.LoaiKhModel
import com.gym.network.RetrofitInstance

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  16/07/2022
 */
class LoaiKhRepository {
    suspend fun getDSLoaiKH(): List<LoaiKhModel>? {
        val request = RetrofitInstance.loadApiLoaiKH.getDSLoaiKH()
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun getLoaiKH(idLoaiKH: Int): LoaiKhModel? {
        val request = RetrofitInstance.loadApiLoaiKH.getLoaiKH(idLoaiKH)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun insertLoaiKH(loaiKhModel: LoaiKhModel): LoaiKhModel?{
        val request = RetrofitInstance.loadApiLoaiKH.insertLoaiKH(loaiKhModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun updateLoaiKH(loaiKhModel: LoaiKhModel): LoaiKhModel?{
        val request = RetrofitInstance.loadApiLoaiKH.updateLoaiKH(loaiKhModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun deleteLoaiKH(idLoaiKH: Int){
        RetrofitInstance.loadApiLoaiKH.deleteLoaiKH(idLoaiKH)
    }
}