package com.gym.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    val loadApiLoaiGT: LoaiGtService by lazy {
        getApiUrl().create(LoaiGtService::class.java)
    }
    val loadApiLoaiKH: LoaiKhService by lazy {
        getApiUrl().create(LoaiKhService::class.java)
    }
    val loadApiGoiTap: GoiTapService by lazy {
        getApiUrl().create(GoiTapService::class.java)
    }
    val loadApiKhachHang: KhachHangService by lazy {
        getApiUrl().create(KhachHangService::class.java)
    }

}