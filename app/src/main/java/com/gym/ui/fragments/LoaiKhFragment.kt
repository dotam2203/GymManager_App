package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.gym.R
import com.gym.databinding.FragmentLoaikhBinding
import com.gym.model.LoaiKhModel
import com.gym.ui.adapter.LoaiKhAdapter
import com.gym.ui.viewmodel.LoaiKhViewModel

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  14/07/2022
 */
class LoaiKhFragment : Fragment(), LoaiKhAdapter.OnItemClick {
    private lateinit var binding: FragmentLoaikhBinding
    var loaiKhAdapter = LoaiKhAdapter(this@LoaiKhFragment)
    var loaiKHs = ArrayList<LoaiKhModel>()
    val viewModel: LoaiKhViewModel by lazy {
        ViewModelProvider(this).get(LoaiKhViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoaikhBinding.inflate(layoutInflater)
        initViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pbLoad.visibility = View.VISIBLE
    }

    fun initViewModel() {
        //call api
        viewModel.getDSLoaiKH()
        //Livedata observer
        viewModel.loaiKHs.observe(viewLifecycleOwner) {response ->
            if (response == null) {
                binding.pbLoad.visibility = View.VISIBLE
                Toast.makeText(activity, "Load api failed!", Toast.LENGTH_SHORT).show()
                return@observe
            }
            else{
                loaiKhAdapter.loaiKHs = response
                loaiKHs = response as ArrayList<LoaiKhModel> /* = java.util.ArrayList<com.gym.model.LoaiKhModel> */
                initAdapter()
                loaiKhAdapter.notifyDataSetChanged()
                binding.pbLoad.visibility = View.GONE
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvLoaiKH.apply {
                layoutManager = LinearLayoutManager(activity!!)
                adapter = loaiKhAdapter
            }
            //loaiKhAdapter.updateData(loaiKHs)
            imbAdd.setOnClickListener {
                dialogInsert()
            }
        }
    }
    fun dialogInsert(){
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_loaigt)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes :WindowManager.LayoutParams = window!!.attributes
        windowAtrributes.gravity = Gravity.CENTER
        window.attributes = windowAtrributes
        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        dialog.setCancelable(false)
        dialog.show()
        val tvTitle: TextView = dialog.findViewById(R.id.tvTitleLoaiGT)
        val txtTenLoaiKH: EditText = dialog.findViewById(R.id.txtTenLoaiGT)
        val tvTenLoaiKH: TextInputLayout = dialog.findViewById(R.id.tvTenLoaiGT)
        val tvTrangThai: TextInputLayout = dialog.findViewById(R.id.tvTTLoaiGT)
        val txtTrangThai: EditText = dialog.findViewById(R.id.txtTTLoaiGT)
        tvTitle.setText("Loại khách hàng")
        tvTenLoaiKH.setHint("Loại khách hàng")
        tvTrangThai.visibility = View.GONE
        txtTrangThai.visibility = View.GONE
        var btnThem: Button = dialog.findViewById(R.id.btnThemLoaiGT)
        var btnHuy: Button = dialog.findViewById(R.id.btnHuyLoaiGT)
        btnThem.setOnClickListener {
            if(txtTenLoaiKH.text.trim().isEmpty()){
                txtTenLoaiKH.error = "Vui lòng không để trống"
                txtTenLoaiKH.requestFocus()
            }
            else {
                //call api
                viewModel.insertLoaiKH(LoaiKhModel(0, txtTenLoaiKH.text.toString()))
                //Livedata observer
                getDataCoroutine("Insert success", "Insert fail")
                txtTenLoaiKH.setText("")
                txtTenLoaiKH.requestFocus()
                initViewModel()
                Log.d("TAG", "size new: ${loaiKHs.size}")
            }
            dialog.show()
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun dialogEdit(loaiKh: LoaiKhModel){
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_loaigt)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes :WindowManager.LayoutParams = window!!.attributes
        windowAtrributes.gravity = Gravity.CENTER
        window.attributes = windowAtrributes
        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        dialog.setCancelable(false)
        dialog.show()
        val tvTitle: TextView = dialog.findViewById(R.id.tvTitleLoaiGT)
        val txtTenLoaiKH: EditText = dialog.findViewById(R.id.txtTenLoaiGT)
        val tvTenLoaiKH: TextInputLayout = dialog.findViewById(R.id.tvTenLoaiGT)
        val tvTrangThai: TextInputLayout = dialog.findViewById(R.id.tvTTLoaiGT)
        val txtTrangThai: EditText = dialog.findViewById(R.id.txtTTLoaiGT)
        tvTitle.setText("Loại khách hàng")
        tvTenLoaiKH.setHint("Loại khách hàng")
        tvTrangThai.visibility = View.GONE
        txtTrangThai.visibility = View.GONE
        var btnThem: Button = dialog.findViewById(R.id.btnThemLoaiGT)
        var btnHuy: Button = dialog.findViewById(R.id.btnHuyLoaiGT)
        txtTenLoaiKH.setText(loaiKh.tenLoaiKH)
        btnThem.setText("Update")
        btnThem.setOnClickListener {
            if(txtTenLoaiKH.text.trim().isEmpty()){
                txtTenLoaiKH.error = "Vui lòng không để trống"
                txtTenLoaiKH.requestFocus()
            }
            else {
                //call api
                viewModel.updateLoaiKH(LoaiKhModel(0, txtTenLoaiKH.text.toString()))
                if(txtTenLoaiKH.text.toString() != loaiKh.tenLoaiKH){
                    //Livedata observer
                    getDataCoroutine("Update success","Update fail")
                    initViewModel()
                    Log.d("TAG", "size new: ${loaiKHs.size}")
                }}
            dialog.show()
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun getDataCoroutine(success: String, fail: String) {
        //Livedata observer
        viewModel.loaiKHs.observe(viewLifecycleOwner) {response ->
            if (response == null) {
                Toast.makeText(activity, fail, Toast.LENGTH_SHORT).show()
                return@observe
            }
            else{
                loaiKhAdapter.loaiKHs = response
                initAdapter()
                loaiKhAdapter.notifyDataSetChanged()
                Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun itemClickEdit(loaiKhModel: LoaiKhModel) {
        dialogEdit(loaiKhModel)
        return
    }

    override fun itemClickDelete(id: Int) {
        viewModel.deleteLoaiKH(id)
        getDataCoroutine("Delete successed!", "Delete failed!")
        initViewModel()
        return
    }
}