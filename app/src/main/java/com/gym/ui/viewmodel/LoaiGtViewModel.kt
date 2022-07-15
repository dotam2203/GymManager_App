package com.gym.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.model.LoaiGtModel
import com.gym.repository.LoaiGtRespository
import kotlinx.coroutines.launch

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
class LoaiGtViewModel : ViewModel() {
    private val loaiGtRespository = LoaiGtRespository()
    //Live Data
    private val _loaiGTs = MutableLiveData<List<LoaiGtModel>>()
    private val _loaiGT = MutableLiveData<LoaiGtModel>()
    val loaiGTs: LiveData<List<LoaiGtModel>?> = _loaiGTs
    val loaiGT: LiveData<LoaiGtModel?> = _loaiGT

    fun getDSLoaiGT() {
        viewModelScope.launch {
            val response = loaiGtRespository.getDSLoaiGT()
            _loaiGTs.postValue(response)
        }
    }
    fun insertLoaiGT(loaiGtModel: LoaiGtModel){
        viewModelScope.launch {
            val response = loaiGtRespository.insertLoaiGT(loaiGtModel)
            _loaiGT.postValue(response)
        }
    }
    fun updateLoaiGT(loaiGtModel: LoaiGtModel){
        viewModelScope.launch {
            val response = loaiGtRespository.updateLoaiGT(loaiGtModel)
            _loaiGT.postValue(response)
        }
    }

}