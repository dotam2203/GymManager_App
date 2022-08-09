package com.gym.repository

import com.gym.model.CtBaiTapModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class CtBaiTapRepository {
    fun getDSCtBaiTap(): Flow<Response<List<CtBaiTapModel>>> = flow {
        val request = RetrofitInstance.loadApiCtBaiTap.getDSCtBaiTap()
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun getDSCtBaiTapTheoGT(maGT: String): Flow<Response<List<CtBaiTapModel>>> = flow{
        val request = RetrofitInstance.loadApiCtBaiTap.getDSCtBaiTapTheoGT(maGT)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun getDSCtBaiTapTheoBT(idBT: Int): Flow<Response<List<CtBaiTapModel>>> = flow{
        val request = RetrofitInstance.loadApiCtBaiTap.getDSCtBaiTapTheoBT(idBT)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun getCtBaiTap(idCTBT: Int): Flow<Response<CtBaiTapModel>> = flow{
        val request = RetrofitInstance.loadApiCtBaiTap.getCtBaiTap(idCTBT)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun insertCtBaiTap(ctBaiTapModel: CtBaiTapModel): Flow<Response<CtBaiTapModel>> = flow{
        val request = RetrofitInstance.loadApiCtBaiTap.insertCtBaiTap(ctBaiTapModel)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    fun updateCtBaiTap(ctBaiTapModel: CtBaiTapModel): Flow<Response<CtBaiTapModel>> = flow{
        val request = RetrofitInstance.loadApiCtBaiTap.insertCtBaiTap(ctBaiTapModel)
        if(request.isSuccessful){
            emit(request)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteCtBaiTap(idCTBT: Int){
        RetrofitInstance.loadApiCtBaiTap.deleteCtBaiTap(idCTBT)
    }
}