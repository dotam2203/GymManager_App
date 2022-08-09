package com.gym.repository

import com.gym.model.CtKhuyenMaiModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class CtKhuyenMaiRepository {
    fun getDSCtKhuyenMai(): Flow<Response<List<CtKhuyenMaiModel>>> = flow {
        val request = RetrofitInstance.loadApiCtKhuyenMai.getDSCtKhuyenMai()
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun getDSCtKhuyenMaiTheoGT(maGT: String): Flow<Response<List<CtKhuyenMaiModel>>> = flow{
        val request = RetrofitInstance.loadApiCtKhuyenMai.getDSCtKhuyenMaiTheoGT(maGT)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun getDSCtKhuyenMaiTheoKM(idKM: Int): Flow<Response<List<CtKhuyenMaiModel>>> = flow{
        val request = RetrofitInstance.loadApiCtKhuyenMai.getDSCtKhuyenMaiTheoKM(idKM)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun getCtKhuyenMai(idCTKM: Int): Flow<Response<CtKhuyenMaiModel>> = flow{
        val request = RetrofitInstance.loadApiCtKhuyenMai.getCtKhuyenMai(idCTKM)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun insertCtKhuyenMai(ctKhuyenMaiModel: CtKhuyenMaiModel): Flow<Response<CtKhuyenMaiModel>> = flow{
        val request = RetrofitInstance.loadApiCtKhuyenMai.insertCtKhuyenMai(ctKhuyenMaiModel)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun updateCtKhuyenMai(ctKhuyenMaiModel: CtKhuyenMaiModel): Flow<Response<CtKhuyenMaiModel>> = flow{
        val request = RetrofitInstance.loadApiCtKhuyenMai.insertCtKhuyenMai(ctKhuyenMaiModel)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteCtKhuyenMai(idCTKM: Int){
        RetrofitInstance.loadApiCtKhuyenMai.deleteCtKhuyenMai(idCTKM)
    }
}