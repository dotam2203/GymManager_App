package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gym.R
import com.gym.databinding.FragmentHomeBinding
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount

class HomeFragment : FragmentNext() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setControl()
        replaceFragment(R.id.fragmentMain, TrangChuFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuTrangChu -> replaceFragment(R.id.fragmentMain, TrangChuFragment())
                R.id.menuThetap -> replaceFragment(R.id.fragmentMain, TheTapFragment())
                R.id.menuHoadon -> replaceFragment(R.id.fragmentMain, HoaDonFragment())
                R.id.menuThongke -> replaceFragment(R.id.fragmentMain, ThongKeFragment())
                R.id.menuTaikhoan -> replaceFragment(R.id.fragmentMain, TaiKhoanFragment())
            }
            true

        }
    }
}
