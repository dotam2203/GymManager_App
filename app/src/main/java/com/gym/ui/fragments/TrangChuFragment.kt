package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.gym.R
import com.gym.databinding.FragmentTrangchuBinding
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount

class TrangChuFragment : FragmentNext() {
    private lateinit var binding: FragmentTrangchuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrangchuBinding.inflate(layoutInflater)
        getDataLogin()
        if(SingletonAccount.taiKhoan?.maQuyen == "Q01"){
            binding.llNhanVien.visibility = View.VISIBLE
        }
        else if(SingletonAccount.taiKhoan?.maQuyen == "Q02"){
            binding.llNhanVien.visibility = View.GONE
        }
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
            llNhanVien.setOnClickListener {
                getFragment(view,R.id.navHomeToNhanVien)
            }
        }
    }
    fun getDataLogin(){
        viewModel.getNhanVien(SingletonAccount.taiKhoan!!.maNV)
        lifecycleScope.launchWhenCreated {
            viewModel.nhanVien.collect{ response ->
                binding.tvUser.text = "Xin chào, ${response?.hoTen}"
            }
        }
    }
}