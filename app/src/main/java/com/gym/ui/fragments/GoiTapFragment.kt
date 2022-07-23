package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentGoitapBinding
import com.gym.model.GiaGtModel
import com.gym.model.GoiTapModel
import com.gym.model.LoaiGtModel
import com.gym.ui.SingletonAccount
import com.gym.ui.adapter.GoiTapAdapter
import com.gym.ui.viewmodel.ViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  14/07/2022
 */
class GoiTapFragment: Fragment() {
    private lateinit var binding: FragmentGoitapBinding
    var goiTapAdapter = GoiTapAdapter()
    var goiTaps = ArrayList<GoiTapModel>()
    var loaiGTs = ArrayList<LoaiGtModel>()
    var tenLoaiGTs = ArrayList<String>()
    var loaiGT = LoaiGtModel()
    val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoitapBinding.inflate(layoutInflater)
        val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        initViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imbAdd.setOnClickListener {
            dialogInsert()
        }
    }
    fun getLoaiGTByMaLoaiGT(idLoaiGT: Int){
        viewModel.getLoaiGT(idLoaiGT)
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGT.collect{response ->
                if (response != null) {
                    loaiGT = response
                }
            }
        }
    }
    fun getTenLoaiGT(){
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect{ response ->
                if(response.isEmpty()){
                    return@collect
                }
                else{
                    loaiGTs = response as ArrayList<LoaiGtModel> /* = java.util.ArrayList<com.gym.model.LoaiGtModel> */
                    for(i in response.indices){
                        tenLoaiGTs.add(loaiGTs[i].tenLoaiGT)
                    }
                }
            }
        }
    }
    fun initViewModel() {
        //call api
        viewModel.getDSGoiTap()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect {response ->
                if (response.isEmpty()) {
                    binding.pbLoad.visibility = View.VISIBLE
                    Toast.makeText(activity, "Load api failed!", Toast.LENGTH_SHORT).show()
                    return@collect
                }
                else{
                    goiTapAdapter.goiTaps = response
                    goiTaps.addAll(response)
                    initAdapter()
                    goiTapAdapter.notifyDataSetChanged()
                    binding.pbLoad.visibility = View.GONE
                }
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvGoiTap.apply {
                layoutManager = LinearLayoutManager(activity!!)
                adapter = goiTapAdapter
            }
            //loaiGtAdapter.updateData(loaiGTs)
            getTenLoaiGT()
        }
    }
    private fun getDataGT(success: String, fail: String) {
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect {response ->
                if (response.isEmpty()) {
                    Toast.makeText(activity, fail, Toast.LENGTH_SHORT).show()
                    return@collect
                }
                else{
                    goiTapAdapter.goiTaps = response
                    initAdapter()
                    goiTapAdapter.notifyDataSetChanged()
                    Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getDataGia(success: String, fail: String) {
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.gias.collect {response ->
                if (response.isEmpty()) {
                    Toast.makeText(activity, fail, Toast.LENGTH_SHORT).show()
                    return@collect
                }
                else{
                    Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun dialogInsert(){
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_goitap)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes : WindowManager.LayoutParams = window!!.attributes
        windowAtrributes.gravity = Gravity.CENTER
        window.attributes = windowAtrributes
        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        dialog.setCancelable(false)
        dialog.show()
        val txtMaGT: EditText = dialog.findViewById(R.id.txtMaGT)
        val txtTenGT: EditText = dialog.findViewById(R.id.txtTenGT)
        val txtTrangThai: EditText = dialog.findViewById(R.id.txtTTGoiTap)
        val txtGiaGT: EditText = dialog.findViewById(R.id.txtGiaGT)
        val txtNgayAD: EditText = dialog.findViewById(R.id.txtNgayAD)
        val txtMoTa: EditText = dialog.findViewById(R.id.txtMoTaGT)
        val btnThem: Button = dialog.findViewById(R.id.btnThemGT)
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyGT)
        //----------------------------spinner--------------------------
        txtTrangThai.apply {
            setText("Hoạt động")
            isEnabled = false
        }
        btnThem.setText("Update")
        var spinner: AutoCompleteTextView = dialog.findViewById(R.id.spLoaiGT)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,tenLoaiGTs)
        var selectItem: String? = ""
        var idLoaiGT: Int = 0
        val maNV: String = SingletonAccount.taiKhoan?.maNV.toString()
        spinner.setAdapter(arrayAdapter)
        spinner.setOnItemClickListener { parent, view, position, id ->
            selectItem = parent.getItemAtPosition(position).toString()
            for(i in loaiGTs.indices){
                if(selectItem == loaiGTs[i].tenLoaiGT){
                    idLoaiGT = loaiGTs[i].idLoaiGT
                    break
                }
            }
        }
        btnThem.setOnClickListener {
            viewModel.insertGoiTap(GoiTapModel(txtMaGT.text.toString(),txtTenGT.text.toString(),txtMoTa.text.toString(),txtTrangThai.text.toString(),idLoaiGT))
            getDataGT("Insert success","Insert fail")
            viewModel.insertGia(GiaGtModel(0,txtNgayAD.text.toString(),txtGiaGT.text.toString(),txtMaGT.text.toString(),maNV))
            getDataGia("Price success","Price fail")
            initViewModel()
        }
        //-------------------------------------------------------------

        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }
}

