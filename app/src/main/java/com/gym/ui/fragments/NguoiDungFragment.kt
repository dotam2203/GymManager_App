package com.gym.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.gym.R
import com.gym.databinding.TabLayoutNguoidungBinding
import com.gym.ui.adapter.NguoiDungViewPagerAdapter

class NguoiDungFragment : Fragment() {
    private lateinit var binding: TabLayoutNguoidungBinding
    val tabTitle = listOf("Khách hàng","Loại khách hàng")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabLayoutNguoidungBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = NguoiDungViewPagerAdapter(childFragmentManager,activity!!.lifecycle)
        TabLayoutMediator(binding.tabLayout,binding.viewPager){
            tab,position -> tab.text = tabTitle[position]
        }.attach()
    }
}