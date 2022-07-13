package com.gym.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.model.LoaiGtModel
import com.gym.network.RetrofitInstance
import com.gym.repository.RequestLoaiGT
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
class LoaiGtViewModel : ViewModel() {
    private val requestLoaiGT = RequestLoaiGT()
    //Live Data
    private val _loaiGTs = MutableLiveData<List<LoaiGtModel>>()
    val loaiGTs: LiveData<List<LoaiGtModel>?> = _loaiGTs
    fun getDSLoaiGT() {
        viewModelScope.launch {
            val response = requestLoaiGT.getDSLoaiGT()
            _loaiGTs.postValue(response)
        }
    }
}