package com.gym.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gym.model.LoaiGTModel
import com.gym.network.LoaiGTService
import com.gym.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
class GymViewModel : ViewModel() {
    //Live Data
    var loaiGTs = MutableLiveData<List<LoaiGTModel>>()
//    var loaiGT = MutableStateFlow<List<LoaiGTModel>>
    fun getDSLoaiGT() {
        RetrofitInstance.getApiUrl().create(LoaiGTService::class.java).getDSLoaiGT().enqueue(object : Callback<List<LoaiGTModel>> {
                override fun onResponse(
                    call: Call<List<LoaiGTModel>>, response: Response<List<LoaiGTModel>>) {
                    loaiGTs.value = response.body()
                }
                override fun onFailure(call: Call<List<LoaiGTModel>>, t: Throwable) {

                }
            })
    }
}