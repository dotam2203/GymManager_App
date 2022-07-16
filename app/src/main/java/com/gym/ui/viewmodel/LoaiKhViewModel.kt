package com.gym.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.model.LoaiKhModel
import com.gym.repository.LoaiKhRepository
import kotlinx.coroutines.launch

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
class LoaiKhViewModel : ViewModel() {
    private val loaiKhRespository = LoaiKhRepository()
    //Live Data
    private val _loaiKHs = MutableLiveData<List<LoaiKhModel>>()
    private val _loaiKH = MutableLiveData<LoaiKhModel>()
    val loaiKHs: LiveData<List<LoaiKhModel>?> = _loaiKHs
    val loaiKH: LiveData<LoaiKhModel?> = _loaiKH

    fun getDSLoaiKH() {
        viewModelScope.launch {
            val response = loaiKhRespository.getDSLoaiKH()
            _loaiKHs.postValue(response)
        }
    }
    fun insertLoaiKH(loaiKhModel: LoaiKhModel){
        viewModelScope.launch {
            val response = loaiKhRespository.insertLoaiKH(loaiKhModel)
            _loaiKH.postValue(response)
        }
    }
    fun updateLoaiKH(loaiKhModel: LoaiKhModel){
        viewModelScope.launch {
            val response = loaiKhRespository.updateLoaiKH(loaiKhModel)
            _loaiKH.postValue(response)
        }
    }
    fun deleteLoaiKH(id: Int){
        viewModelScope.launch {
            loaiKhRespository.deleteLoaiKH(id)
        }
    }

}