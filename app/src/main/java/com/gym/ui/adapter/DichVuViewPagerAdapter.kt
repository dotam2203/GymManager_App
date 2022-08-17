package com.gym.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gym.ui.fragments.GoiTapFragment
import com.gym.ui.fragments.LoaiGtFragment

class DichVuViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2;
    }
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> GoiTapFragment()
            1 -> LoaiGtFragment()
            else -> GoiTapFragment()
        }
    }
}