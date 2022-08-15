package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.gym.databinding.FragmentDsDangkyBinding
import com.gym.model.KhachHangModel
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.DsTheTapAdapter
import kotlinx.coroutines.delay

class DSDangKyFragment : FragmentNext(){
    private lateinit var binding: FragmentDsDangkyBinding
    var khachHang = KhachHangModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDsDangkyBinding.inflate(layoutInflater)
        reviceDataKH()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshData()
    }
    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                reviceDataKH()
                refresh.isRefreshing = false
            }
        }
    }
    fun reviceDataKH(){
        parentFragmentManager.setFragmentResultListener("passData",this,object : FragmentResultListener {
            override fun onFragmentResult(requestKey: String, result: Bundle) {
                khachHang = result.getParcelable("dataKH")?: return
            }
        })
    }
    fun initViewModel(){
        viewModel.getDSTheTap()
        lifecycleScope.launchWhenCreated {
            viewModel.theTaps.collect{

            }
        }
    }
    private fun initAdapter(){
        binding.apply {

        }
    }
}