package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.internal.LifecycleCallback.getFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gym.R
import com.gym.databinding.TabLayoutDichvuBinding
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.DichVuViewPagerAdapter

class DichVuFragment : FragmentNext() {
    private lateinit var binding: TabLayoutDichvuBinding
    val tabTitle = listOf("Gói Tập","Loại Gói Tập")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabLayoutDichvuBinding.inflate(layoutInflater)
        binding.imbBack.setOnClickListener {
            getFragment(requireView(), R.id.navDichvuToHome)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPager.adapter = DichVuViewPagerAdapter(childFragmentManager, requireActivity().lifecycle)
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout,binding.viewPager){
            tab,position -> tab.text = tabTitle[position]
        }.attach()
    }
}