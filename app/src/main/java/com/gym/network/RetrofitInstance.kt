package com.gym.network

import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
object RetrofitInstance {
    val BASE_URL = "https://gym-manager-api.herokuapp.com/"
    //val BASE_URL = "http://localhost:8888/"
    fun getApiUrl(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(OkHttpClient().newBuilder().also { client ->
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addNetworkInterceptor(loggingInterceptor)
                client.connectTimeout(120, TimeUnit.SECONDS)
                client.writeTimeout(120, TimeUnit.SECONDS)
                client.protocols(Collections.singletonList(Protocol.HTTP_1_1))
            }.build())
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
    val loadApiNhanVien: NhanVienService by lazy {
        getApiUrl().create(NhanVienService::class.java)
    }
    val loadApiPhanQuyen: PhanQuyenService by lazy {
        getApiUrl().create(PhanQuyenService::class.java)
    }
    val loadApiTaiKhoan: TaiKhoanService by lazy {
        getApiUrl().create(TaiKhoanService::class.java)
    }
    val loadApiGiaGT: GiaGtService by lazy {
        getApiUrl().create(GiaGtService::class.java)
    }
    val loadApiHoaDon: HoaDonService by lazy {
        getApiUrl().create(HoaDonService::class.java)
    }
    val loadApiTheTap: TheTapService by lazy {
        getApiUrl().create(TheTapService::class.java)
    }
    val loadApiBaiTap: BaiTapService by lazy {
        getApiUrl().create(BaiTapService::class.java)
    }
    val loadApiKhuyenMai: KhuyenMaiService by lazy {
        getApiUrl().create(KhuyenMaiService::class.java)
    }
    val loadApiCtKhuyenMai: CtKhuyenMaiService by lazy {
        getApiUrl().create(CtKhuyenMaiService::class.java)
    }
    val loadApiCtBaiTap: CtBaiTapService by lazy {
        getApiUrl().create(CtBaiTapService::class.java)
    }
    val loadApiCtTheTap: CtTheTapService by lazy {
        getApiUrl().create(CtTheTapService::class.java)
    }

}