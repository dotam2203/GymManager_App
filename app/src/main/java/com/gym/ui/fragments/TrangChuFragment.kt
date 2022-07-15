package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.gym.R
import com.gym.databinding.FragmentTrangchuBinding
import com.gym.ui.FragmentNext

class TrangChuFragment : FragmentNext() {
    private lateinit var binding: FragmentTrangchuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrangchuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            llDichVu.setOnClickListener {
                getFragment(view, R.id.navHomeToDichVu)
            }
            llKhachHang.setOnClickListener {
                getFragment(view, R.id.navHomeToNguoiDung)
            }
        }
    }
}