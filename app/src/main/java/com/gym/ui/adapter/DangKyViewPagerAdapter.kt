package com.gym.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gym.ui.fragments.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  26/07/2022
 */
class DangKyViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, ): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return DangKyFragment()
            1 -> return DSDangKyFragment()
            else -> return DangKyFragment()
        }
    }
}