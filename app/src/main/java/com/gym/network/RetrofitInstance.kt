package com.gym.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
object RetrofitInstance {
    val BASE_URL = "https://gym-manager-api.herokuapp.com/"
    fun getApiUrl(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val loadApiLoaiGT = getApiUrl().create(LoaiGTService::class.java)
}