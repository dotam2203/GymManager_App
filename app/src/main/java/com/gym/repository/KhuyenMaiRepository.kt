package com.gym.repository

import com.gym.model.KhuyenMaiModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class KhuyenMaiRepository {
    fun getDSKhuyenMai(): Flow<Response<List<KhuyenMaiModel>>> = flow{
        val request = RetrofitInstance.loadApiKhuyenMai.getDSKhuyenMai()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Dispatchers.IO)
    fun getDSKhuyenMaiTheoNV(maNV: String): Flow<Response<List<KhuyenMaiModel>>> = flow{
        val request = RetrofitInstance.loadApiKhuyenMai.getDSKhuyenMaiTheoNV(maNV)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Dispatchers.IO)
    fun getKhuyenMai(idKM: Int): Flow<Response<KhuyenMaiModel>> = flow{
        val request = RetrofitInstance.loadApiKhuyenMai.getKhuyenMai(idKM)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Dispatchers.IO)

    fun insertKhuyenMai(khuyenMaiModel: KhuyenMaiModel): Flow<Response<KhuyenMaiModel>> = flow{
        val request = RetrofitInstance.loadApiKhuyenMai.insertKhuyenMai(khuyenMaiModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Dispatchers.IO)
    fun updateKhuyenMai(khuyenMaiModel: KhuyenMaiModel): Flow<Response<KhuyenMaiModel>> = flow{
        val request = RetrofitInstance.loadApiKhuyenMai.updateKhuyenMai(khuyenMaiModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Dispatchers.IO)
    suspend fun deleteKhuyenMai(idKM: Int){
        RetrofitInstance.loadApiKhuyenMai.deleteKhuyenMai(idKM)
    }
}