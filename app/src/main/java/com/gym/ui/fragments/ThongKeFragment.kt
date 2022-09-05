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
import com.gym.model.CtTheTapModel
import com.gym.model.GoiTapModel
import com.gym.model.ThongKeModel
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.ThongKeAdapter
import kotlinx.coroutines.delay

class ThongKeFragment : FragmentNext() {
    private lateinit var binding: FragmentThongkeBinding
    var thongKeAdapter = ThongKeAdapter()
    var ctThes = ArrayList<CtTheTapModel>()
    var goiTaps = ArrayList<GoiTapModel>()
    var thongKes = ArrayList<ThongKeModel>()
    var ctThe = CtTheTapModel()
    var thongKe = ThongKeModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThongkeBinding.inflate(layoutInflater)
        getDSGoiTap()
        //binding.pbLoad.visibility = View.VISIBLE
        binding.apply {
            spDV.setText("Dịch vụ")
            txtNgBD.setText(getCurrentDate())
            txtNgKT.setText(getCurrentDate())
            constraint.visibility = View.GONE
        }
        getEvent()
        initAdapter()
        return binding.root
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
                pbLoad.visibility = View.VISIBLE
                thongKes.clear()
                Log.e("NgayBD", "NgayBD: ${getFormatDateApi(txtNgBD.text.toString().trim())} ")
                Log.e("NgayKT", "NgayKT: ${getFormatDateApi(txtNgKT.text.toString().trim())}")
                lifecycleScope.launchWhenCreated {
                    initViewModel(getFormatDateApi(txtNgBD.text.toString().trim()),getFormatDateApi(txtNgKT.text.toString().trim()))
                    delay(1000L)
                    pbLoad.visibility = View.GONE
                    constraint.visibility = View.VISIBLE
                }
            }
            val loaiTKs = ArrayList<String>()
            var selectDV = ""
            for(i in goiTaps.indices){
                loaiTKs.add(goiTaps[i].tenGT)
            }
            val arrAdapterLoaiTK = ArrayAdapter(requireContext(), R.layout.dropdown_item,loaiTKs)
            spDV.setAdapter(arrAdapterLoaiTK)
            spDV.setOnItemClickListener { parent, view, position, id ->
                selectDV = parent.getItemAtPosition(position).toString()
            }
        }
    }

    fun initViewModel(ngayBD: String, ngayKT: String){


        viewModel.getLocDSCtTheTap(ngayBD, ngayKT)
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

    private fun initAdapter() {
        binding.apply {
            rvThongKe.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = thongKeAdapter
            }
        }
    }
}