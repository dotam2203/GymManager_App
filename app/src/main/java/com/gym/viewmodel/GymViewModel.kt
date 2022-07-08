package com.gym.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gym.model.LoaiGTModel
import com.gym.network.LoaiGTService
import com.gym.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
class GymViewModel : ViewModel() {
    //Live Data
    var dataList = MutableLiveData<List<LoaiGTModel>>()

    fun getApiData() {
        RetrofitInstance.getApiUrl().create(LoaiGTService::class.java).getDSLoaiGT().enqueue(object : Callback<List<LoaiGTModel>> {
                override fun onResponse(
                    call: Call<List<LoaiGTModel>>, response: Response<List<LoaiGTModel>>) {
                    dataList.value = response.body()
                }

                override fun onFailure(call: Call<List<LoaiGTModel>>, t: Throwable) {

                }
            })
    }
}