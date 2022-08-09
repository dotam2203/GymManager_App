package com.gym.repository

import com.gym.model.TheTapModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class TheTapRepository {
    fun getDSTheTap(): Flow<Response<List<TheTapModel>>> = flow{
        val request = RetrofitInstance.loadApiTheTap.getDSTheTap()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getDSTheTapTheoKH(maKH: String): Flow<Response<List<TheTapModel>>> = flow{
        val request = RetrofitInstance.loadApiTheTap.getDSTheTapTheoKH(maKH)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getTheTap(maThe: String): Flow<Response<TheTapModel>> = flow{
        val request = RetrofitInstance.loadApiTheTap.getTheTap(maThe)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)

    fun insertTheTap(theTapModel: TheTapModel): Flow<Response<TheTapModel>> = flow{
        val request = RetrofitInstance.loadApiTheTap.insertTheTap(theTapModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun updateTheTap(theTapModel: TheTapModel): Flow<Response<TheTapModel>> = flow{
        val request = RetrofitInstance.loadApiTheTap.updateTheTap(theTapModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    suspend fun deleteTheTap(maThe: String){
        RetrofitInstance.loadApiTheTap.deleteTheTap(maThe)
    }
}