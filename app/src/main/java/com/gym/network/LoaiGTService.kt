package com.gym.network

import com.gym.model.LoaiGTModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
interface LoaiGTService {
    @GET("loaigt")
    fun getDSLoaiGT(): Call<List<LoaiGTModel>>
}