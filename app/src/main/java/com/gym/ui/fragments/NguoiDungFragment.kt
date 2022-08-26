package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.gym.R
import com.gym.databinding.TabLayoutDichvuBinding
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.NguoiDungViewPagerAdapter

class NguoiDungFragment : FragmentNext() {
    private lateinit var binding: TabLayoutDichvuBinding
    val tabTitle = listOf("Khách hàng","Loại khách hàng")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabLayoutDichvuBinding.inflate(layoutInflater)
        binding.imbBack.setOnClickListener {
            getFragment(requireView(),R.id.navNguoiDungToHome)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = NguoiDungViewPagerAdapter(childFragmentManager,activity!!.lifecycle)
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout,binding.viewPager){
            tab,position -> tab.text = tabTitle[position]
        }.attach()
    }
}