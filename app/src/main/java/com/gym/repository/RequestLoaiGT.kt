package com.gym.repository

import com.gym.model.LoaiGtModel
import com.gym.network.RetrofitInstance
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  13/07/2022
 */
class RequestLoaiGT {
    suspend fun getDSLoaiGT(): List<LoaiGtModel>? {
        val request = RetrofitInstance.loadApiLoaiGT.getDSLoaiGT()
        if(request.isSuccessful)
            return request.body()!!
        return null
    }
}