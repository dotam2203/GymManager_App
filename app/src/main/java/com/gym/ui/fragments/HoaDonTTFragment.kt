package com.gym.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentHoadonTtBinding
import com.gym.model.HoaDonModel
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.HoaDonTTAdapter
import kotlinx.coroutines.delay

class HoaDonTTFragment : FragmentNext(),HoaDonTTAdapter.OnItemClick {
    private lateinit var binding: FragmentHoadonTtBinding
    var hoaDonTTAdapter = HoaDonTTAdapter(this@HoaDonTTFragment)
    var hoaDons = ArrayList<HoaDonModel>()
    var hoaDon = HoaDonModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHoadonTtBinding.inflate(layoutInflater)
        refreshData()
        binding.pbLoad.visibility = View.VISIBLE
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            initViewModel()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                initViewModel()
                refresh.isRefreshing = false
            }

        }
    }
    fun initViewModel() {
        //call api
        viewModel.getDSHoaDonTheoNgayGiam()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.hoaDons.collect {response ->
                if (response.isEmpty()) {
                    binding.pbLoad.visibility = View.GONE
                    return@collect
                }
                else{
                    hoaDonTTAdapter.hoaDons.addAll(response)
                    hoaDons.addAll(response)
                    initAdapter()
                    hoaDonTTAdapter.notifyDataSetChanged()
                    for(i in hoaDons.indices){
                    }
                    binding.pbLoad.visibility = View.GONE
                }
            }
        }
    }
    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvHoaDonTT.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = hoaDonTTAdapter
            }
        }
    }
    override fun itemClickInfo(hoaDonModel: HoaDonModel) {
    }

    override fun itemClickSelect(maHD: String) {
    }
}