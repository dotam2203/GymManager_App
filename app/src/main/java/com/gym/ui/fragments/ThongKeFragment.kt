package com.gym.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentThongkeBinding
import com.gym.model.*
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.ThongKeAdapter
import kotlinx.coroutines.delay

class ThongKeFragment : FragmentNext() {
    private lateinit var binding: FragmentThongkeBinding
    var thongKeAdapter = ThongKeAdapter()
    var goiTaps = ArrayList<GoiTapModel>()
    var thongKes = ArrayList<ThongKeModel>()
    var khachHangs = ArrayList<KhachHangModel>()
    var thes = ArrayList<TheTapModel>()
    var ctThe = CtTheTapModel()
    var item = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThongkeBinding.inflate(layoutInflater)
        getDSGoiTap()
        binding.apply {
            txtNgBD.setText(getCurrentDate())
            txtNgKT.setText(getCurrentDate())
            constraint.visibility = View.GONE
        }
        getEvent()
        initAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDSKhachHang()
        setControl()
    }

    private fun setControl() {
        val options = arrayListOf("Doanh thu theo tháng", "Doanh thu theo dịch vụ", "Top 10 khách hàng tiềm năng")
        Log.e("option", "setControl: $options", )
        binding.apply {
            item = 0
            val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item, options)
            spDV.setAdapter(arrayAdapter)
            spDV.setOnItemClickListener { parent, view, position, id ->
                item = position
            }
        }
    }

    private fun getDSGoiTap(){
        viewModel.getDSGoiTap()
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect{
                if(it.isNotEmpty()){
                    goiTaps.addAll(it)
                }
                else return@collect
            }
        }
    }

    private fun getEvent() {
        binding.apply {
            txtNgBD.setOnClickListener {
                val datePickerFragment = DatePicker2Fragment()
                val support = requireActivity().supportFragmentManager
                //receive date
                support.setFragmentResultListener("REQUEST_KEY", viewLifecycleOwner) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("PASS_DATE")
                        txtNgBD.setText(date)
                    }
                }
                //show dialog
                datePickerFragment.show(support, "DatePickerFragment")
            }
            txtNgKT.setOnClickListener {
                val datePickerFragment = DatePicker2Fragment()
                val support = requireActivity().supportFragmentManager
                //receive date
                support.setFragmentResultListener("REQUEST_KEY", viewLifecycleOwner) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("PASS_DATE")
                        txtNgKT.setText(date)
                    }
                }
                //show dialog
                datePickerFragment.show(support, "DatePickerFragment")
                constraint.visibility = View.GONE
            }
            btnLoc.setOnClickListener {
                if(item == 0){
                    pbLoad.visibility = View.VISIBLE
                    thongKes.clear()
                    Log.e("NgayBD", "NgayBD: ${getFormatDateApi(txtNgBD.text.toString().trim())} ")
                    Log.e("NgayKT", "NgayKT: ${getFormatDateApi(txtNgKT.text.toString().trim())}")
                    lifecycleScope.launchWhenCreated {
                        thongKeTheoThang(getFormatDateApi(txtNgBD.text.toString().trim()),getFormatDateApi(txtNgKT.text.toString().trim()))
                        delay(1000L)
                        pbLoad.visibility = View.GONE
                        constraint.visibility = View.VISIBLE
                    }
                }
                else if(item == 1){
                    pbLoad.visibility = View.VISIBLE
                    thongKes.clear()
                    Log.e("NgayBD", "NgayBD: ${getFormatDateApi(txtNgBD.text.toString().trim())} ")
                    Log.e("NgayKT", "NgayKT: ${getFormatDateApi(txtNgKT.text.toString().trim())}")
                    lifecycleScope.launchWhenCreated {
                        thongKeTheoDichVu(getFormatDateApi(txtNgBD.text.toString().trim()),getFormatDateApi(txtNgKT.text.toString().trim()))
                        delay(1000L)
                        pbLoad.visibility = View.GONE
                        constraint.visibility = View.VISIBLE
                    }
                }
                else if(item == 2){
                    pbLoad.visibility = View.VISIBLE
                    thongKes.clear()
                    lifecycleScope.launchWhenCreated {
                        top10KHTiemNang(getFormatDateApi(txtNgBD.text.toString().trim()),getFormatDateApi(txtNgKT.text.toString().trim()))
                        delay(1000L)
                        pbLoad.visibility = View.GONE
                        constraint.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun thongKeTheoThang(ngayBD: String, ngayKT: String){
        viewModel.getDSCtTheTapThang(ngayBD, ngayKT)
        lifecycleScope.launchWhenCreated {
            viewModel.ctTheTaps.collect{
                if(it.isNotEmpty()){
                    thongKes.clear()
                    var sum: Long = 0
                    thongKeAdapter.thongKes.clear()
                    for(i in it.indices){
                        thongKes.add(ThongKeModel(it[i].donGia,it[i].maGT,it[i].tenGT,it[i].ngayDK))
                        sum += tongDoanhThu(it[i].donGia)
                    }
                    thongKeAdapter.thongKes.addAll(thongKes)
                    thongKeAdapter.notifyDataSetChanged()
                    binding.tvTongDT.text = "${formatMoney(sum.toString().trim())} đ"
                }
                else {
                    return@collect
                }
            }
        }
    }
    fun thongKeTheoDichVu(ngayBD: String, ngayKT: String){
        viewModel.getDSCtTheTapTheoDV(ngayBD, ngayKT)
        lifecycleScope.launchWhenCreated {
            viewModel.ctTheTaps.collect{
                if(it.isNotEmpty()){
                    thongKes.clear()
                    var sum: Long = 0
                    thongKeAdapter.thongKes.clear()
                    for(i in it.indices){
                        thongKes.add(ThongKeModel(it[i].donGia,it[i].maGT,it[i].tenGT,it[i].ngayDK))
                        sum += tongDoanhThu(it[i].donGia)
                    }
                    thongKeAdapter.thongKes.addAll(thongKes)
                    thongKeAdapter.notifyDataSetChanged()
                    binding.tvTongDT.text = "${formatMoney(sum.toString().trim())} đ"
                }
                else {
                    return@collect
                }
            }
        }
    }
    fun getDSKhachHang(){
        viewModel.getDSKhachHang()
        lifecycleScope.launchWhenCreated {
            viewModel.khachHangs.collect{
                if(it.isNotEmpty()){
                    khachHangs.addAll(it)
                }
                else return@collect
            }
        }
    }
    fun getDSTheTheoKH(maKH: String){
        viewModel.getDSTheTapTheoKH(maKH)
        lifecycleScope.launchWhenCreated {
            viewModel.theTaps.collect{
                if(it.isNotEmpty()){
                    thes.addAll(it)
                }
                else return@collect
            }
        }
    }
    fun getCtTheTheoTheTap(maThe: String){
        viewModel.getCtTheTapTheoThe(maThe)
        lifecycleScope.launchWhenCreated {
            viewModel.ctTheTap.collect{
                if(it != null){
                    ctThe = it
                }
                else return@collect
            }
        }
    }
    fun top10KHTiemNang(ngayBD: String, ngayKT: String){
        var sum: Long = 0
        for(i in khachHangs.indices){
            getDSTheTheoKH(khachHangs[i].maKH)
            for(j in thes.indices){
                getCtTheTheoTheTap(thes[i].maThe)
                sum += tongDoanhThu(ctThe.donGia)
            }
        }
    }
    private fun initAdapter() {
        binding.apply {
            rvThongKe.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = thongKeAdapter
            }
        }
    }
}