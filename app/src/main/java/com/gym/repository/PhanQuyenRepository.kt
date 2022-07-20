package com.gym.repository

import com.gym.model.PhanQuyenModel
import com.gym.network.RetrofitInstance

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  10/07/2022
 */
class PhanQuyenRepository {
    suspend fun getDSQuyen(): List<PhanQuyenModel>? {
        val request = RetrofitInstance.loadApiPhanQuyen.getDSQuyen()
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun getQuyen(maQuyen: String): PhanQuyenModel? {
        val request = RetrofitInstance.loadApiPhanQuyen.getQuyen(maQuyen)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun insertQuyen(loaiGtModel: PhanQuyenModel): PhanQuyenModel?{
        val request = RetrofitInstance.loadApiPhanQuyen.insertQuyen(loaiGtModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun updateQuyen(loaiGtModel: PhanQuyenModel): PhanQuyenModel?{
        val request = RetrofitInstance.loadApiPhanQuyen.updateQuyen(loaiGtModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun deleteQuyen(maQuyen: String){
        RetrofitInstance.loadApiPhanQuyen.deleteQuyen(maQuyen)
    }
}