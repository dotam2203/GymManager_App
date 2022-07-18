package com.gym.repository

import com.gym.model.GoiTapModel
import com.gym.network.RetrofitInstance

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  19/07/2022
 */
class GoiTapRepository {
    suspend fun getDSGoiTap(): List<GoiTapModel>? {
        val request = RetrofitInstance.loadApiGoiTap.getDSGoiTap()
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun getDSGoiTapTheoLoaiGT(idLoaiGT: Int): List<GoiTapModel>? {
        val request = RetrofitInstance.loadApiGoiTap.getDSGoiTapTheoLoaiGT(idLoaiGT)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun getGoiTap(maGT: String): GoiTapModel? {
        val request = RetrofitInstance.loadApiGoiTap.getGoiTap(maGT)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }

    suspend fun insertGoiTap(goiTapModel: GoiTapModel): GoiTapModel?{
        val request = RetrofitInstance.loadApiGoiTap.insertGoiTap(goiTapModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun updateGoiTap(goiTapModel: GoiTapModel): GoiTapModel?{
        val request = RetrofitInstance.loadApiGoiTap.updateGoiTap(goiTapModel)
        if (request.isSuccessful)
            return request.body()!!
        return null
    }
    suspend fun deleteGoiTap(maGT: String){
        RetrofitInstance.loadApiGoiTap.deleteGoiTap(maGT)
    }
}