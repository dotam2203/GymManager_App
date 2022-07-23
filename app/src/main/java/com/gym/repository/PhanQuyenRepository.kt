package com.gym.repository

import com.gym.model.PhanQuyenModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  10/07/2022
 */
class PhanQuyenRepository {
    fun getDSQuyen(): Flow<Response<List<PhanQuyenModel>>> = flow {
        val request = RetrofitInstance.loadApiPhanQuyen.getDSQuyen()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun getQuyen(maQuyen: String): Flow<Response<PhanQuyenModel>> = flow {
        val request = RetrofitInstance.loadApiPhanQuyen.getQuyen(maQuyen)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun insertQuyen(loaiGtModel: PhanQuyenModel): Flow<Response<PhanQuyenModel>> = flow{
        val request = RetrofitInstance.loadApiPhanQuyen.insertQuyen(loaiGtModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun updateQuyen(loaiGtModel: PhanQuyenModel): Flow<Response<PhanQuyenModel>> = flow{
        val request = RetrofitInstance.loadApiPhanQuyen.updateQuyen(loaiGtModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    suspend fun deleteQuyen(maQuyen: String){
        RetrofitInstance.loadApiPhanQuyen.deleteQuyen(maQuyen)
    }
}