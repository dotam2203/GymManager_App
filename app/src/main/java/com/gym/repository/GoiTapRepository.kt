package com.gym.repository

import com.gym.model.GoiTapModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  19/07/2022
 */
class GoiTapRepository {
    fun getDSGoiTap(): Flow<Response<List<GoiTapModel>>> = flow{
        val request = RetrofitInstance.loadApiGoiTap.getDSGoiTap()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun getDSGoiTapTheoLoaiGT(idLoaiGT: Int): Flow<Response<List<GoiTapModel>>> = flow{
        val request = RetrofitInstance.loadApiGoiTap.getDSGoiTapTheoLoaiGT(idLoaiGT)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun getGoiTap(maGT: String): Flow<Response<GoiTapModel>> = flow{
        val request = RetrofitInstance.loadApiGoiTap.getGoiTap(maGT)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)

    fun insertGoiTap(goiTapModel: GoiTapModel): Flow<Response<GoiTapModel>> = flow{
        val request = RetrofitInstance.loadApiGoiTap.insertGoiTap(goiTapModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    fun updateGoiTap(goiTapModel: GoiTapModel): Flow<Response<GoiTapModel>> = flow{
        val request = RetrofitInstance.loadApiGoiTap.updateGoiTap(goiTapModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(Main)
    suspend fun deleteGoiTap(maGT: String){
        RetrofitInstance.loadApiGoiTap.deleteGoiTap(maGT)
    }
}