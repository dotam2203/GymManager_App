package com.gym.repository

import com.gym.model.KhachHangModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.IO
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
    }.flowOn(IO)
    fun getDSKhachHangTheoLoaiKH(idLoaiKH: Int): Flow<Response<List<KhachHangModel>>> = flow{
        val request = RetrofitInstance.loadApiKhachHang.getDSKhachHangTheoLoaiKH(idLoaiKH)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getKhachHang(maKH: String): Flow<Response<KhachHangModel>> = flow{
        val request = RetrofitInstance.loadApiKhachHang.getKhachHang(maKH)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)

    fun insertKhachHang(khachHangModel: KhachHangModel): Flow<Response<KhachHangModel>> = flow{
        val request = RetrofitInstance.loadApiKhachHang.insertKhachHang(khachHangModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun updateKhachHang(khachHangModel: KhachHangModel): Flow<Response<KhachHangModel>> = flow{
        val request = RetrofitInstance.loadApiKhachHang.updateKhachHang(khachHangModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    suspend fun deleteKhachHang(maKH: String){
        RetrofitInstance.loadApiKhachHang.deleteKhachHang(maKH)
    }
}