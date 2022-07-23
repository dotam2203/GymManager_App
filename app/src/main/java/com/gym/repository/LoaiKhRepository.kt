package com.gym.repository

import com.gym.model.LoaiKhModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  16/07/2022
 */
class LoaiKhRepository {
    fun getDSLoaiKH(): Flow<Response<List<LoaiKhModel>>> = flow {
        val request = RetrofitInstance.loadApiLoaiKH.getDSLoaiKH()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun getLoaiKH(idLoaiKH: Int): Flow<Response<LoaiKhModel>> = flow{
        val request = RetrofitInstance.loadApiLoaiKH.getLoaiKH(idLoaiKH)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun insertLoaiKH(loaiKhModel: LoaiKhModel): Flow<Response<LoaiKhModel>> = flow{
        val request = RetrofitInstance.loadApiLoaiKH.insertLoaiKH(loaiKhModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun updateLoaiKH(loaiKhModel: LoaiKhModel): Flow<Response<LoaiKhModel>> = flow{
        val request = RetrofitInstance.loadApiLoaiKH.updateLoaiKH(loaiKhModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    suspend fun deleteLoaiKH(idLoaiKH: Int){
        RetrofitInstance.loadApiLoaiKH.deleteLoaiKH(idLoaiKH)
    }
}