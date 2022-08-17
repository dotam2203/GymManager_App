package com.gym.repository

import com.gym.model.HoaDonModel
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class HoaDonRepository {
    fun getDSHoaDon(): Flow<Response<List<HoaDonModel>>> = flow{
        val request = RetrofitInstance.loadApiHoaDon.getDSHoaDon()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getDSHoaDonTheoNgayGiam(): Flow<Response<List<HoaDonModel>>> = flow{
        val request = RetrofitInstance.loadApiHoaDon.getDSHoaDonTheoNgayGiam()
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getDSHoaDonTheoNV(maNV: String): Flow<Response<List<HoaDonModel>>> = flow{
        val request = RetrofitInstance.loadApiHoaDon.getDSHoaDonTheoNV(maNV)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getDSHoaDonTheoThe(maThe: String): Flow<Response<List<HoaDonModel>>> = flow{
        val request = RetrofitInstance.loadApiHoaDon.getDSHoaDonTheoThe(maThe)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun getHoaDon(maHD: String): Flow<Response<HoaDonModel>> = flow{
        val request = RetrofitInstance.loadApiHoaDon.getHoaDon(maHD)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)

    fun insertHoaDon(hoaDonModel: HoaDonModel): Flow<Response<HoaDonModel>> = flow{
        val request = RetrofitInstance.loadApiHoaDon.insertHoaDon(hoaDonModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    fun updateHoaDon(hoaDonModel: HoaDonModel): Flow<Response<HoaDonModel>> = flow{
        val request = RetrofitInstance.loadApiHoaDon.updateHoaDon(hoaDonModel)
        if (request.isSuccessful)
            emit(request)
    }.flowOn(IO)
    suspend fun deleteHoaDon(maHD: String){
        RetrofitInstance.loadApiHoaDon.deleteHoaDon(maHD)
    }
}