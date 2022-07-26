package com.gym.repository

import com.gym.model.NhanVienModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  20/07/2022
 */
class NhanVienRepository {
    fun getDSNhanVien(): Flow<Response<List<NhanVienModel>>> = flow{
        val request = RetrofitInstance.loadApiNhanVien.getDSNhanVien()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getNhanVien(maNV: String): Flow<Response<NhanVienModel>> = flow{
        val request = RetrofitInstance.loadApiNhanVien.getNhanVien(maNV)
        if(request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun insertNhanVien(nhanVienModel: NhanVienModel): Flow<Response<NhanVienModel>> = flow{
        val request = RetrofitInstance.loadApiNhanVien.insertNhanVien(nhanVienModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun updateNhanVien(nhanVienModel: NhanVienModel): Flow<Response<NhanVienModel>> = flow{
        val request = RetrofitInstance.loadApiNhanVien.updateNhanVien(nhanVienModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    suspend fun deleteNhanVien(maNV: String){
        RetrofitInstance.loadApiNhanVien.deleteNhanVien(maNV)
    }
}