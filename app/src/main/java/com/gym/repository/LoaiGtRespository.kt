package com.gym.repository

import com.gym.model.LoaiGtModel
import com.gym.network.RetrofitInstance

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  13/07/2022
 */
class LoaiGtRespository {
    suspend fun getDSLoaiGT(): List<LoaiGtModel>? {
        val request = RetrofitInstance.loadApiLoaiGT.getDSLoaiGT()
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun insertLoaiGT(loaiGtModel: LoaiGtModel): LoaiGtModel?{
        val request = RetrofitInstance.loadApiLoaiGT.insertLoaiGT(loaiGtModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun updateLoaiGT(loaiGtModel: LoaiGtModel): LoaiGtModel?{
        val request = RetrofitInstance.loadApiLoaiGT.updateLoaiGT(loaiGtModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun deleteLoaiGT(idLoaiGT: Int): LoaiGtModel?{
        val request = RetrofitInstance.loadApiLoaiGT.deleteLoaiGT(idLoaiGT)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }

}
