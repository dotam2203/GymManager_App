package com.gym.repository

import com.gym.model.GiaGtModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.Main
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
    }.flowOn(Main)

    fun getDSGiaTheoGoiTap(maGT: String): Flow<Response<List<GiaGtModel>>> = flow{
        val request = RetrofitInstance.loadApiGiaGT.getDSGiaTheoGoiTap(maGT)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Main)

    fun getDSGiaTheoNhanVien(maNV: String): Flow<Response<List<GiaGtModel>>> = flow{
        val request = RetrofitInstance.loadApiGiaGT.getDSGiaTheoNhanVien(maNV)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Main)

    fun getGia(idGia: Int): Flow<Response<GiaGtModel>> = flow{
        val request = RetrofitInstance.loadApiGiaGT.getGia(idGia)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Main)

    fun insertGia(goiTapModel: GiaGtModel): Flow<Response<GiaGtModel>> = flow{
        val request = RetrofitInstance.loadApiGiaGT.insertGia(goiTapModel)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Main)

    fun updateGia(goiTapModel: GiaGtModel): Flow<Response<GiaGtModel>> = flow{
        val request = RetrofitInstance.loadApiGiaGT.updateGia(goiTapModel)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Main)

    suspend fun deleteGia(idGia: Int){
        RetrofitInstance.loadApiGiaGT.deleteGia(idGia)
    }
}