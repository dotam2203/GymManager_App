package com.gym.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.model.LoaiGtModel
import com.gym.repository.LoaiGtRepository
import kotlinx.coroutines.launch

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
class LoaiGtViewModel : ViewModel() {
    private val loaiGtRepository = LoaiGtRepository()
    //Live Data
    private val _loaiGTs = MutableLiveData<List<LoaiGtModel>>()
    private val _loaiGT = MutableLiveData<LoaiGtModel>()
    val loaiGTs: LiveData<List<LoaiGtModel>?> = _loaiGTs
    val loaiGT: LiveData<LoaiGtModel?> = _loaiGT

    fun getDSLoaiGT() {
        viewModelScope.launch {
            val response = loaiGtRepository.getDSLoaiGT()
            _loaiGTs.postValue(response)
        }
    }
    fun getLoaiGT(idLoaiGT: Int) {
        viewModelScope.launch {
            val response = loaiGtRepository.getLoaiGT(idLoaiGT)
            _loaiGT.postValue(response)
        }
    }
    fun insertLoaiGT(loaiGtModel: LoaiGtModel){
        viewModelScope.launch {
            val response = loaiGtRepository.insertLoaiGT(loaiGtModel)
            _loaiGT.postValue(response)
        }
    }
    fun updateLoaiGT(loaiGtModel: LoaiGtModel){
        viewModelScope.launch {
            val response = loaiGtRepository.updateLoaiGT(loaiGtModel)
            _loaiGT.postValue(response)
        }
    }
    fun deleteLoaiGT(id: Int){
        viewModelScope.launch {
            loaiGtRepository.deleteLoaiGT(id)
        }
    }

}