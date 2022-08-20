package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gym.databinding.TabLayoutHoadonBinding
import com.gym.ui.adapter.HoaDonViewPagerAdapter

class HoaDonFragment : Fragment() {
    private lateinit var binding: TabLayoutHoadonBinding
    val tabTitle = listOf("Chờ thanh toán","Đã thanh toán")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabLayoutHoadonBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = HoaDonViewPagerAdapter(childFragmentManager,activity!!.lifecycle)
        TabLayoutMediator(binding.tabLayout,binding.viewPager){
            tab,position -> tab.text = tabTitle[position]
        }.attach()
    }
}