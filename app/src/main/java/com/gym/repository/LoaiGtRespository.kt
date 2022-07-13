package com.gym.repository

import com.gym.model.LoaiGtModel
import com.gym.network.LoaiGtService
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  13/07/2022
 */
class LoaiGtRespository(private val loaiGtService: LoaiGtService){
    suspend fun getDSLoaiGT(): Response<List<LoaiGtModel>>{
        return loaiGtService.getDSLoaiGT()
    }
}