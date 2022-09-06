package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.gym.R
import com.gym.databinding.FragmentTrangchuBinding
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount

class TrangChuFragment : FragmentNext() {
    private lateinit var binding: FragmentTrangchuBinding
    private val slideModel = ArrayList<SlideModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrangchuBinding.inflate(layoutInflater)
        initSlide()
        getDataLogin()
        if(SingletonAccount.taiKhoan?.maQuyen == "Q01"){
            binding.llNhanVien.visibility = View.VISIBLE
            binding.qlNV.visibility = View.VISIBLE
        }
        else if(SingletonAccount.taiKhoan?.maQuyen == "Q02"){
            binding.llNhanVien.visibility = View.GONE
            binding.qlNV.visibility = View.GONE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            binding.imgSlide.setImageList(slideModel, ScaleTypes.FIT)
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
    private fun initSlide() {
        slideModel.add(SlideModel("https://inkythuatso.com/uploads/thumbnails/800/2022/06/hinh-nen-gym-4k-dep-cho-dien-thoai-7-08-11-16-36.jpg", ScaleTypes.FIT))
        slideModel.add(SlideModel("https://st.depositphotos.com/1062035/4071/i/600/depositphotos_40719707-stock-photo-fitness-with-barbell.jpg", ScaleTypes.FIT))
        slideModel.add(SlideModel("https://bigdata-vn.com/wp-content/uploads/2021/10/1633768829_218_Hinh-anh-hinh-nen-Gym-dep-an-tuong-chan-thuc.jpg", ScaleTypes.FIT))
        slideModel.add(SlideModel("https://img5.thuthuatphanmem.vn/uploads/2021/12/28/anh-nen-gym-chat_032108916.jpg", ScaleTypes.FIT))
        slideModel.add(SlideModel("https://img4.goodfon.com/wallpaper/nbig/a/e3/fitness-female-workout-girl-gym.jpg", ScaleTypes.FIT))
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