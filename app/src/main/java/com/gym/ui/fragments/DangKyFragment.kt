package com.gym.ui.fragments

import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.gym.R
import com.gym.databinding.FragmentDangkyBinding
import com.gym.model.GiaGtModel
import com.gym.model.GoiTapModel
import com.gym.model.KhachHangModel
import com.gym.model.LoaiGtModel
import com.gym.ui.FragmentNext
import kotlinx.coroutines.delay
import java.util.Collections.addAll

class DangKyFragment : FragmentNext() {
    private lateinit var binding: FragmentDangkyBinding
    var khachHang = KhachHangModel()
    val loaiGTs = ArrayList<LoaiGtModel>()
    val goiTaps = ArrayList<GoiTapModel>()
    val gias = ArrayList<GiaGtModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDangkyBinding.inflate(layoutInflater)
        binding.txtNgayBD.setText(getCurrentDate())
        reviceDataKH()
        //getInitViewModel()
        getEnvent()
        getInitCalendar()
        Toast.makeText(requireActivity(), "Hello ${khachHang.hoTen}", Toast.LENGTH_SHORT).show()

        return binding.root
    }

    private fun reviceDataKH() {
        parentFragmentManager.setFragmentResultListener("passData",this,object :FragmentResultListener{
            override fun onFragmentResult(requestKey: String, result: Bundle) {
                khachHang = result.getParcelable("dataKH")?: return
                binding.txtGia.text = khachHang.hoTen
            }
        })
    }

    private fun getInitViewModel() {
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect{
                if(it.isNotEmpty()){
                    loaiGTs.addAll(it)
                }
            }
        }
        viewModel.getDSGoiTap()
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect{
                if(it.isNotEmpty()){
                    goiTaps.addAll(it)
                }
            }
        }
        viewModel.getDSGia()
        lifecycleScope.launchWhenCreated {
            viewModel.gias.collect{
                if(it.isNotEmpty()){
                    gias.addAll(it)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getEnvent() {
        var selectLoaiGT: String = ""
        var selectGT: String = ""
        var selectSL: String = ""
        binding.apply {
            val tenLoaiGTs: ArrayList<String> = getListTenLoaiGT()
            Log.e("Error", "loaiGT array: ${getListTenLoaiGT().size} ")
            val arrAdapterLoaiGT = ArrayAdapter(requireContext(), R.layout.dropdown_item, tenLoaiGTs)
            spLoaiGT.setAdapter(arrAdapterLoaiGT)
            spLoaiGT.setOnItemClickListener { parent, view, position, id ->
                spGoiTap.setText("")
                spCTLoaiGT.setText("")
                selectLoaiGT = parent.getItemAtPosition(position).toString()
                //-------------------------Số lượng đăng ký-----------------------
                val soLuongs: ArrayList<String> = getSoLuongLoaiGT(selectLoaiGT)
                val arrAdapterSL = ArrayAdapter(requireContext(), R.layout.dropdown_item, soLuongs)
                spCTLoaiGT.setAdapter(arrAdapterSL)
                spCTLoaiGT.setOnItemClickListener { parent, view, position, id ->
                    selectSL = parent.getItemAtPosition(position).toString()
                }
                //---------------------------------------------------------------
                //--------------------------Gói tập----------------------------
                val tenGTs: ArrayList<String> = getListTenGTByIDLoaiGT(getIDByTenLoaiGT(selectLoaiGT))
                val arrAdapterGT = ArrayAdapter(requireContext(), R.layout.dropdown_item, tenGTs)
                spGoiTap.setAdapter(arrAdapterGT)
                spGoiTap.setOnItemClickListener { parent, view, position, id ->
                    selectGT = parent.getItemAtPosition(position).toString()
                    //---------------------------------------------------------------
                }
            }
        }
    }

    private fun getInitCalendar() {
        binding.apply {
            imbCalendar.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                val support = requireActivity().supportFragmentManager
                //receive date
                support.setFragmentResultListener("REQUEST_KEY", viewLifecycleOwner) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("PASS_DATE")
                        txtNgayBD.setText(date)
                    }
                }
                //show dialog
                datePickerFragment.show(support, "DatePickerFragment")
            }
        }
    }

    //================================================
    //--------------------Loại gt----------------------
    fun getListTenLoaiGT(): ArrayList<String> {
        //Log.e("Error", "getListTenLoaiGT: ${listLoaiGT.size}", )
        var tenLoaiGTs: ArrayList<String> = arrayListOf()
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect{
                for (i in it.indices) {
                    tenLoaiGTs.add(it[i].tenLoaiGT)
                }
            }
        }

        return tenLoaiGTs
    }

    fun getIDByTenLoaiGT(ten: String): Int {
        var id: Int = 0
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect{
                for (i in it.indices) {
                    if (ten == it[i].tenLoaiGT) {
                        id = it[i].idLoaiGT
                        break
                    }
                }
            }
        }
        return id
    }

    //--------------------------------------------
    //-------------------Gói Tập------------------
    fun getListTenGTByIDLoaiGT(id: Int): ArrayList<String>{
        var tenGTs = ArrayList<String>()
        lifecycleScope.launchWhenCreated {
            viewModel.getDSGoiTapTheoLoaiGT(id)
            viewModel.goiTaps.collect{
                Log.e("Error", "check tenGTs: ${it.size}", )
                if(it.isNotEmpty()){
                    for(i in it.indices){
                        binding.spGoiTap.isEnabled = true
                        tenGTs.add(it[i].tenGT)
                    }
                }
                else if(it.isEmpty()){
                    binding.spGoiTap.isEnabled = false
                    return@collect
                }
            }
        }
        return tenGTs
    }
    fun getMaByTenGT(ten: String): String {
        var ma: String = ""
        viewModel.getDSGoiTap()
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect{
                for (i in it.indices) {
                    if (ten == it[i].tenGT) {
                        ma = it[i].maGT
                        break
                    }
                }
            }
        }
        return ma
    }
//============================================
}