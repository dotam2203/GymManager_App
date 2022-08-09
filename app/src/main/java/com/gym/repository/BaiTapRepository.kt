package com.gym.repository

import com.gym.model.BaiTapModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class BaiTapRepository {
    fun getDSBaiTap(): Flow<Response<List<BaiTapModel>>> = flow {
        val request = RetrofitInstance.loadApiBaiTap.getDSBaiTap()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getBaiTap(idBT: Int): Flow<Response<BaiTapModel>> = flow{
        val request = RetrofitInstance.loadApiBaiTap.getBaiTap(idBT)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun insertBaiTap(baiTapModel: BaiTapModel): Flow<Response<BaiTapModel>> = flow{
        val request = RetrofitInstance.loadApiBaiTap.insertBaiTap(baiTapModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun updateBaiTap(baiTapModel: BaiTapModel): Flow<Response<BaiTapModel>> = flow{
        val request = RetrofitInstance.loadApiBaiTap.updateBaiTap(baiTapModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    suspend fun deleteBaiTap(idBT: Int){
        RetrofitInstance.loadApiBaiTap.deleteBaiTap(idBT)
    }
}