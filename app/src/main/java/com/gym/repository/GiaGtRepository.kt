package com.gym.repository

import com.gym.model.GiaGtModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class GiaGtRepository {
    fun getDSGia(): Flow<Response<List<GiaGtModel>>> = flow {
        val request = RetrofitInstance.loadApiGiaGT.getDSGia()
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(IO)

    fun getDSGiaTheoGoiTap(maGT: String): Flow<Response<List<GiaGtModel>>> = flow{
        val request = RetrofitInstance.loadApiGiaGT.getDSGiaTheoGoiTap(maGT)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(IO)

    fun getDSGiaTheoNhanVien(maNV: String): Flow<Response<List<GiaGtModel>>> = flow{
        val request = RetrofitInstance.loadApiGiaGT.getDSGiaTheoNhanVien(maNV)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(IO)

    fun getGia(idGia: Int): Flow<Response<GiaGtModel>> = flow{
        val request = RetrofitInstance.loadApiGiaGT.getGia(idGia)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(IO)

    fun insertGia(giaModel: GiaGtModel): Flow<Response<GiaGtModel>> = flow{
        val request = RetrofitInstance.loadApiGiaGT.insertGia(giaModel)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(IO)

    fun updateGia(giaModel: GiaGtModel): Flow<Response<GiaGtModel>> = flow{
        val request = RetrofitInstance.loadApiGiaGT.updateGia(giaModel)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(IO)

    suspend fun deleteGiaGoiTap(idGia: Int, maGT: String){
        val request = RetrofitInstance.loadApiGiaGT.deleteGia(idGia)
//        if(request.isSuccessful)
            RetrofitInstance.loadApiGoiTap.deleteGoiTap(maGT)
    }
}