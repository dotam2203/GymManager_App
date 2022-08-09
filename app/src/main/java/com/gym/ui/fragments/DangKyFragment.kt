package com.gym.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.gym.R
import com.gym.databinding.FragmentDangkyBinding
import com.gym.model.GiaGtModel
import com.gym.model.GoiTapModel
import com.gym.model.LoaiGtModel
import com.gym.ui.FragmentNext
import kotlinx.coroutines.delay
import java.util.Collections.addAll

class DangKyFragment : FragmentNext() {
    private lateinit var binding: FragmentDangkyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDangkyBinding.inflate(layoutInflater)
        binding.txtNgayBD.setText(getCurrentDate())
        getEnvent()
        getInitCalendar()
        return binding.root
    }

   /* private fun initViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getDSLoaiGT()
            viewModel.loaiGTs.collect {
                if (it.isNotEmpty()) {
                    viewModel.listLoaiGT.addAll(it)
                } else
                    return@collect
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.getDSGoiTap()
            viewModel.goiTaps.collect {
                if (it.isNotEmpty()) {
                    listGT.addAll(it)
                } else
                    return@collect
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.getDSGia()
            viewModel.gias.collect {
                if (it.isNotEmpty()) {
                    listGia.addAll(it)
                } else
                    return@collect
            }
        }

    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getEnvent() {
        var selectLoaiGT: String = ""
        var selectGT: String = ""
        binding.apply {
            val loaiGTs: ArrayList<String> = getListTenLoaiGT()
            Log.e("Error", "loaiGT array: ${getListTenLoaiGT().size} ")
            val arrAdapterLoaiGT = ArrayAdapter(requireContext(), R.layout.dropdown_item, loaiGTs)
            spLoaiGT.setAdapter(arrAdapterLoaiGT)
            spLoaiGT.setOnItemClickListener { parent, view, position, id ->
                selectLoaiGT = parent.getItemAtPosition(position).toString()
                val goiTaps: ArrayList<String> = getListTenGTByIDLoaiGT(getIDByTenLoaiGT(selectLoaiGT))
                val arrAdapterGT = ArrayAdapter(requireContext(), R.layout.dropdown_item, goiTaps)
                spGoiTap.setAdapter(arrAdapterLoaiGT)
                spGoiTap.setOnItemClickListener { parent, view, position, id ->
                    selectGT = parent.getItemAtPosition(position).toString()
                }
            }
            //lấy ds gói tập
            /*spGoiTap.isEnabled = true
            spCTLoaiGT.isEnabled = true*/

            /*if(selectLoaiGT.isEmpty()){
                spGoiTap.isEnabled = false
                spCTLoaiGT.isEnabled = false
            }
            else if (selectLoaiGT.trim().isNotEmpty()){
                spGoiTap.isEnabled = true
                spCTLoaiGT.isEnabled = true
                val goiTaps: ArrayList<String> = getListTenGT(getIDByTenLoaiGT(selectLoaiGT))
                val arrAdapterGT = ArrayAdapter(requireContext(), R.layout.dropdown_item, goiTaps)
                spGoiTap.setAdapter(arrAdapterLoaiGT)
                spGoiTap.setOnItemClickListener { parent, view, position, id ->
                    selectGT = parent.getItemAtPosition(position).toString()
                }
            }*/
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
        var loaiGTs: ArrayList<String> = arrayListOf()
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect{
                for (i in it.indices) {
                    loaiGTs.add(it[i].tenLoaiGT)
                }
            }
        }

        return loaiGTs
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
        var list = ArrayList<String>()
        viewModel.getDSGoiTapTheoLoaiGT(id)
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect{
                Log.e("Errrororror", "check list: ${it.size}", )
                if(it.isNotEmpty()){
                    for(i in it.indices){
                        list.add(it[i].tenGT)
                    }
                }
            }
        }
        Log.e("Error", "getListTenGTByIDLoaiGT: ${list.size}", )
        return list
    }
    fun getListTenGT(idLoaiGT: Int): ArrayList<String> {
        val goiTaps = ArrayList<String>()
        viewModel.getDSGoiTap()
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect{
                for (i in it.indices) {
                    if (idLoaiGT == it[i].idLoaiGT) {
                        goiTaps.add(it[i].tenGT)
                    }
                }
            }
        }
        return goiTaps
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