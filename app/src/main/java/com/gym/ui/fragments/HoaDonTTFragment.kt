package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.UniversalTimeScale.toLong
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentHoadonTtBinding
import com.gym.firebase.NotificationHelper
import com.gym.model.*
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.HoaDonTTAdapter
import kotlinx.coroutines.delay

class HoaDonTTFragment : FragmentNext(),HoaDonTTAdapter.OnItemClick {
    private lateinit var binding: FragmentHoadonTtBinding
    var hoaDonTTAdapter = HoaDonTTAdapter(this@HoaDonTTFragment)
    var hoaDons = ArrayList<HoaDonModel>()
    var hoaDon = HoaDonModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHoadonTtBinding.inflate(layoutInflater)
        refreshData()
        binding.pbLoad.visibility = View.VISIBLE
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            initViewModel()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                initViewModel()
                refresh.isRefreshing = false
            }

        }
    }
    fun initViewModel() {
        //call api
        viewModel.getDSHoaDonTheoNgayGiam()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.hoaDons.collect {response ->
                if (response.isEmpty()) {
                    binding.pbLoad.visibility = View.GONE
                    return@collect
                }
                else{
                    hoaDonTTAdapter.hoaDons.addAll(response)
                    hoaDons.addAll(response)
                    initAdapter()
                    hoaDonTTAdapter.notifyDataSetChanged()
                    for(i in hoaDons.indices){
                    }
                    binding.pbLoad.visibility = View.GONE
                }
            }
        }
    }
    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvHoaDonTT.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = hoaDonTTAdapter
            }
        }
    }
    override fun itemClickInfo(hoaDonModel: HoaDonModel) {
       // dialogBillInfo(hoaDonModel)
    }

    override fun itemClickSelect(maHD: String) {
    }
    /*fun dialogBillInfo(hoaDon: HoaDonModel) {
        val dialog = Dialog(activity!!)
        var ctTTModel = CtTheTapModel()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_thanhtoan)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes: WindowManager.LayoutParams = window!!.attributes
        windowAtrributes.gravity = Gravity.CENTER
        window.attributes = windowAtrributes
        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        dialog.setCancelable(true)
        dialog.show()
        //----------------------------------------------------
        val tvHDMess: TextView = dialog.findViewById(R.id.tvHDMessage)
        val tvKHDK: TextView = dialog.findViewById(R.id.tvKHDK)
        val tvSDT: TextView = dialog.findViewById(R.id.tvSdtDK)
        val tvNgayLap: TextView = dialog.findViewById(R.id.tvNgayLap)
        val tvNgayBD: TextView = dialog.findViewById(R.id.tvBDTap)
        val tvNgayKT: TextView = dialog.findViewById(R.id.tvKTTap)
        val tvHDSo: TextView = dialog.findViewById(R.id.tvHDSo)
        val tvNVLap: TextView = dialog.findViewById(R.id.tvNVLap)
        val tvDichVu: TextView = dialog.findViewById(R.id.tvDichVu)
        val tvLoaiDV: TextView = dialog.findViewById(R.id.tvLoaiDV)
        val tvDonGia: TextView = dialog.findViewById(R.id.tvDonGia)
        val tvSoLuong: TextView = dialog.findViewById(R.id.tvSoLuong)
        val tvKhuyenMai: TextView = dialog.findViewById(R.id.tvKhuyenMai)
        val tvGiamTien: TextView = dialog.findViewById(R.id.tvGiamTien)
        val tvThanhTien: TextView = dialog.findViewById(R.id.tvThanhTien)
        val tvNoiDung: TextView = dialog.findViewById(R.id.tvNoiDung)
        //-----------------------------------------------------
        val btnThanhToan: Button = dialog.findViewById(R.id.btnThanhToan)
        val btnThanhToanSau: Button = dialog.findViewById(R.id.btnTTSau)
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyTT)
        //----------------------
        tvKHDK.text = hoaDon.tenKH
        tvSDT.text = hoaDon.sdt
        tvNgayLap.text = hoaDon.ngayLap
//        tvNgayBD.text = getFormatDate(hoaDon.ctTheTaps!![0].ngayBD)
//        tvNgayKT.text = getFormatDate(hoaDon.ctTheTaps!![0].ngayKT)
        tvHDSo.text = hoaDon.maHD
        tvNVLap.text = getTenNV(hoaDon.maNV)
        tvDichVu.text = hoaDon.ctTheTaps!![0].tenGT
        tvNoiDung.text = getMoTa(hoaDon.ctTheTaps!![0].maGT)
        tvLoaiDV.text = hoaDon.ctTheTaps!![0].tenLoaiGT
        tvDonGia.text = "${formatMoney(getGiaGT(hoaDon.ctTheTaps!![0].maGT))} đ"
        //tvSoLuong.text = select.substring(0,1)
        tvKhuyenMai.text = "0%"
        tvGiamTien.text = "0"
        tvThanhTien.text = hoaDon.ctTheTaps!![0].donGia
        //----------------------
        btnThanhToanSau.visibility = View.GONE
        btnThanhToan.visibility = View.GONE
        btnHuy.visibility = View.GONE
    }*/
    fun getTenNV(maNV: String): String{
        var nv = ""
        viewModel.getNhanVien(maNV)
        lifecycleScope.launchWhenCreated {
            viewModel.nhanVien.collect{
                if(it != null)
                    nv = it.hoTen
            }
        }
        return nv
    }
    fun getMoTa(maGT: String): String{
        var moTa = ""
        viewModel.getGoiTap(maGT)
        lifecycleScope.launchWhenCreated {
            viewModel.goiTap.collect{
                if(it != null)
                    moTa = it.moTa
            }
        }
        return moTa
    }
    fun getGiaGT(maGT: String): String{
        var gia = ""
        viewModel.getDSGiaTheoGoiTap(maGT)
        lifecycleScope.launchWhenCreated {
            viewModel.gias.collect{
                if(it.isNotEmpty())
                    gia = it[0].gia.trim()
            }
        }
        return gia
    }

}