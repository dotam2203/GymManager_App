package com.gym.repository

import com.gym.model.CtTheTapModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.util.*

class CtTheTapRepository {
    fun getDSCtTheTap(): Flow<Response<List<CtTheTapModel>>> = flow {
        val request = RetrofitInstance.loadApiCtTheTap.getDSCtTheTapTheoNgayBD()
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun getDSCtTheTapThang(ngayBD: String, ngayKT: String): Flow<Response<List<CtTheTapModel>>> = flow {
        val request = RetrofitInstance.loadApiCtTheTap.getDSCtTheTapThang(ngayBD,ngayKT)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun getDSCtTheTapTheoDV(ngayBD: String, ngayKT: String): Flow<Response<List<CtTheTapModel>>> = flow {
        val request = RetrofitInstance.loadApiCtTheTap.getDSCtTheTapTheoDV(ngayBD,ngayKT)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)


    fun getDSCtTheTapTheoGT(maGT: String): Flow<Response<List<CtTheTapModel>>> = flow{
        val request = RetrofitInstance.loadApiCtTheTap.getDSCtTheTapTheoGT(maGT)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun getCtTheTapTheoThe(maThe: String): Flow<Response<CtTheTapModel>> = flow{
        val request = RetrofitInstance.loadApiCtTheTap.getCtTheTapTheoThe(maThe)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)
    fun getDSCtTheTapTheoHD(maHD: String): Flow<Response<List<CtTheTapModel>>> = flow {
        val request = RetrofitInstance.loadApiCtTheTap.getDSCtTheTapTheoHD(maHD)
        if (request.isSuccessful) {
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun getCtTheTap(idCTThe: Int): Flow<Response<CtTheTapModel>> = flow{
        val request = RetrofitInstance.loadApiCtTheTap.getCtTheTap(idCTThe)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun insertCtTheTap(ctTheTapModel: CtTheTapModel): Flow<Response<CtTheTapModel>> = flow{
        val request = RetrofitInstance.loadApiCtTheTap.insertCtTheTap(ctTheTapModel)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun updateCtTheTap(ctTheTapModel: CtTheTapModel): Flow<Response<CtTheTapModel>> = flow{
        val request = RetrofitInstance.loadApiCtTheTap.insertCtTheTap(ctTheTapModel)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteCtTheTap(idCTThe: Int){
        RetrofitInstance.loadApiCtTheTap.deleteCtTheTap(idCTThe)
    }
}