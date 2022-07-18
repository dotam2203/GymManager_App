package com.gym.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.model.KhachHangModel
import com.gym.repository.KhachHangRepository
import kotlinx.coroutines.launch

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  19/07/2022
 */
class KhachHangViewModel: ViewModel() {
    private val khachHangRepository = KhachHangRepository()
    //Live data
    private val _khachHangs = MutableLiveData<List<KhachHangModel>>()
    private val _khachHang = MutableLiveData<KhachHangModel>()
    val khachHangs: LiveData<List<KhachHangModel>?> = _khachHangs
    val khachHang: LiveData<KhachHangModel?> = _khachHang

    fun getDSKhachHang() {
        viewModelScope.launch {
            val response = khachHangRepository.getDSKhachHang()
            _khachHangs.postValue(response)
        }
    }
    fun getDSKhachHangTheoLoaiKH(idLoaiKH: Int) {
        viewModelScope.launch {
            val response = khachHangRepository.getDSKhachHangTheoLoaiKH(idLoaiKH)
            _khachHangs.postValue(response)
        }
    }
    fun getKhachHang(maKH: String) {
        viewModelScope.launch {
            val response = khachHangRepository.getKhachHang(maKH)
            _khachHang.postValue(response)
        }
    }
    fun insertKhachHang(KhachHangModel: KhachHangModel){
        viewModelScope.launch {
            val response = khachHangRepository.insertKhachHang(KhachHangModel)
            _khachHang.postValue(response)
        }
    }
    fun updateKhachHang(KhachHangModel: KhachHangModel){
        viewModelScope.launch {
            val response = khachHangRepository.updateKhachHang(KhachHangModel)
            _khachHang.postValue(response)
            //_khachHang.value = response
        }
    }
    fun deleteKhachHang(maKH: String){
        viewModelScope.launch {
            khachHangRepository.deleteKhachHang(maKH)
        }
    }

}