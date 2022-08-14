package com.gym.repository

import com.gym.model.LoaiGtModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class LoaiGtRepository {
    suspend fun getDSLoaiGT(): Flow<Response<List<LoaiGtModel>>> = flow {
        val request = RetrofitInstance.loadApiLoaiGT.getDSLoaiGT()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)

    suspend fun getLoaiGT(idLoaiGT: Int): Flow<Response<LoaiGtModel>> = flow {
        val request = RetrofitInstance.loadApiLoaiGT.getLoaiGT(idLoaiGT)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)

    suspend fun insertLoaiGT(loaiGtModel: LoaiGtModel): Flow<Response<LoaiGtModel>> = flow {
        val request = RetrofitInstance.loadApiLoaiGT.insertLoaiGT(loaiGtModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)

    suspend fun updateLoaiGT(loaiGtModel: LoaiGtModel): Flow<Response<LoaiGtModel>> = flow {
        val request = RetrofitInstance.loadApiLoaiGT.updateLoaiGT(loaiGtModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)

    suspend fun deleteLoaiGT(idLoaiGT: Int) {
        RetrofitInstance.loadApiLoaiGT.deleteLoaiGT(idLoaiGT)
    }

}
