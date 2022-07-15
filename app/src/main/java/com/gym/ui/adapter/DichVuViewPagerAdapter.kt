package com.gym.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gym.ui.fragments.GoiTapFragment
import com.gym.ui.fragments.LoaiGtFragment

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  14/07/2022
 */
class DichVuViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return GoiTapFragment()
            1 -> return LoaiGtFragment()
            else -> return GoiTapFragment()
        }
    }
}