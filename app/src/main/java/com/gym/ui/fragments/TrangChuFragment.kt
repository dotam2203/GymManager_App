package com.gym.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.gym.R
import com.gym.databinding.FragmentTrangchuBinding
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount
import com.gym.ui.viewmodel.ViewModel

class TrangChuFragment : FragmentNext() {
    private lateinit var binding: FragmentTrangchuBinding
    val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrangchuBinding.inflate(layoutInflater)
        getDataLogin()
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
    fun getDataLogin(){
        viewModel.getNhanVien(SingletonAccount.taiKhoan!!.maNV.toString())
        viewModel.nhanVien.observe(viewLifecycleOwner){ response ->
            binding.tvUser.text = response!!.hoTen
        }

    }
}