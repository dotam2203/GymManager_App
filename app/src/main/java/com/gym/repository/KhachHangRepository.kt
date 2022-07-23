package com.gym.repository

import com.gym.model.KhachHangModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  19/07/2022
 */
class KhachHangRepository {
    fun getDSKhachHang(): Flow<Response<List<KhachHangModel>>> = flow{
        val request = RetrofitInstance.loadApiKhachHang.getDSKhachHang()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun getDSKhachHangTheoLoaiKH(idLoaiKH: Int): Flow<Response<List<KhachHangModel>>> = flow{
        val request = RetrofitInstance.loadApiKhachHang.getDSKhachHangTheoLoaiKH(idLoaiKH)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun getKhachHang(maGT: String): Flow<Response<KhachHangModel>> = flow{
        val request = RetrofitInstance.loadApiKhachHang.getKhachHang(maGT)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)

    fun insertKhachHang(khachHangModel: KhachHangModel): Flow<Response<KhachHangModel>> = flow{
        val request = RetrofitInstance.loadApiKhachHang.insertKhachHang(khachHangModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun updateKhachHang(khachHangModel: KhachHangModel): Flow<Response<KhachHangModel>> = flow{
        val request = RetrofitInstance.loadApiKhachHang.updateKhachHang(khachHangModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    suspend fun deleteKhachHang(maGT: String){
        RetrofitInstance.loadApiKhachHang.deleteKhachHang(maGT)
    }
}