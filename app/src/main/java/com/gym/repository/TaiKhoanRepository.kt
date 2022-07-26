package com.gym.repository

import com.gym.model.TaiKhoanModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
class TaiKhoanRepository {
    fun getDSTaiKhoan(): Flow<Response<List<TaiKhoanModel>>> = flow {
        val request = RetrofitInstance.loadApiTaiKhoan.getDSTaiKhoan()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getDSTaiKhoanTheoQuyen(maQuyen: String): Flow<Response<List<TaiKhoanModel>>> = flow {
        val request = RetrofitInstance.loadApiTaiKhoan.getDSTaiKhoanTheoQuyen(maQuyen)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getTaiKhoan(maTK: String): Flow<Response<TaiKhoanModel>> = flow {
        val request = RetrofitInstance.loadApiTaiKhoan.getTaiKhoan(maTK)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)

    fun insertTaiKhoan(goiTapModel: TaiKhoanModel): Flow<Response<TaiKhoanModel>> = flow{
        val request = RetrofitInstance.loadApiTaiKhoan.insertTaiKhoan(goiTapModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun updateTaiKhoan(goiTapModel: TaiKhoanModel): Flow<Response<TaiKhoanModel>> = flow{
        val request = RetrofitInstance.loadApiTaiKhoan.updateTaiKhoan(goiTapModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    suspend fun deleteTaiKhoan(maTK: String){
        RetrofitInstance.loadApiTaiKhoan.deleteTaiKhoan(maTK)
    }
}