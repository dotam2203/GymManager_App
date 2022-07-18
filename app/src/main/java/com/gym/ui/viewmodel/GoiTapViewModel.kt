package com.gym.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.model.GoiTapModel
import com.gym.repository.GoiTapRepository
import kotlinx.coroutines.launch

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  19/07/2022
 */
class GoiTapViewModel: ViewModel() {
    private val goiTapRepository = GoiTapRepository()
    //Live data
    private val _goiTaps = MutableLiveData<List<GoiTapModel>>()
    private val _goiTap = MutableLiveData<GoiTapModel>()
    val goiTaps: LiveData<List<GoiTapModel>?> = _goiTaps
    val goiTap: LiveData<GoiTapModel?> = _goiTap

    fun getDSGoiTap() {
        viewModelScope.launch {
            val response = goiTapRepository.getDSGoiTap()
            _goiTaps.postValue(response)
        }
    }
    fun getDSGoiTapTheoLoaiGT(idLoaiGT: Int) {
        viewModelScope.launch {
            val response = goiTapRepository.getDSGoiTapTheoLoaiGT(idLoaiGT)
            _goiTaps.postValue(response)
        }
    }
    fun getGoiTap(maGT: String) {
        viewModelScope.launch {
            val response = goiTapRepository.getGoiTap(maGT)
            _goiTap.postValue(response)
        }
    }
    fun insertGoiTap(GoiTapModel: GoiTapModel){
        viewModelScope.launch {
            val response = goiTapRepository.insertGoiTap(GoiTapModel)
            _goiTap.postValue(response)
        }
    }
    fun updateGoiTap(GoiTapModel: GoiTapModel){
        viewModelScope.launch {
            val response = goiTapRepository.updateGoiTap(GoiTapModel)
            _goiTap.postValue(response)
            //_goiTap.value = response
        }
    }
    fun deleteGoiTap(maGT: String){
        viewModelScope.launch {
            goiTapRepository.deleteGoiTap(maGT)
        }
    }
}