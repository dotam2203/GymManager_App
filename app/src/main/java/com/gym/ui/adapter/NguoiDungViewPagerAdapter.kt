package com.gym.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gym.ui.fragments.KhachHangFragment
import com.gym.ui.fragments.LoaiKhFragment

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  15/07/2022
 */
class NguoiDungViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return KhachHangFragment()
            1 -> return LoaiKhFragment()
            else -> return KhachHangFragment()
        }
    }
}