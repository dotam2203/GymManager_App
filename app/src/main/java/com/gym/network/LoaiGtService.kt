package com.gym.network

import com.gym.model.LoaiGtModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
interface LoaiGtService {
    @GET("loaigt")
    suspend fun getDSLoaiGT(): Response<List<LoaiGtModel>>
}