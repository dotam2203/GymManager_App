package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentLoaigtBinding
import com.gym.model.LoaiGtModel
import com.gym.ui.adapter.LoaiGtAdapter
import com.gym.ui.viewmodel.LoaiGtViewModel

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  14/07/2022
 */
class LoaiGtFragment : Fragment(), LoaiGtAdapter.OnItemClick {
    private lateinit var binding: FragmentLoaigtBinding
    var loaiGtAdapter = LoaiGtAdapter(this@LoaiGtFragment)
    var loaiGTs = ArrayList<LoaiGtModel>()
    val viewModel: LoaiGtViewModel by lazy {
        ViewModelProvider(this).get(LoaiGtViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoaigtBinding.inflate(layoutInflater)
        initViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pbLoad.visibility = View.VISIBLE
    }

    fun initViewModel() {
        //call api
        viewModel.getDSLoaiGT()
        //Livedata observer
        viewModel.loaiGTs.observe(viewLifecycleOwner) {response ->
            if (response == null) {
                binding.pbLoad.visibility = View.VISIBLE
                Toast.makeText(activity, "Load api failed!", Toast.LENGTH_SHORT).show()
                return@observe
            }
            else{
                loaiGtAdapter.loaiGTs = response
                loaiGTs = response as ArrayList<LoaiGtModel> /* = java.util.ArrayList<com.gym.model.LoaiGtModel> */
                initAdapter()
                loaiGtAdapter.notifyDataSetChanged()
                binding.pbLoad.visibility = View.GONE
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvLoaiGT.apply {
                layoutManager = LinearLayoutManager(activity!!)
                adapter = loaiGtAdapter
            }
            //loaiGtAdapter.updateData(loaiGTs)
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
        val txtTenLoaiGT: EditText = dialog.findViewById(R.id.txtTenLoaiGT)
        val txtTrangThai: EditText = dialog.findViewById(R.id.txtTTLoaiGT)
        var btnThem: Button = dialog.findViewById(R.id.btnThemLoaiGT)
        var btnHuy: Button = dialog.findViewById(R.id.btnHuyLoaiGT)
        btnThem.setOnClickListener {
            if(txtTenLoaiGT.text.trim().isEmpty()){
                txtTenLoaiGT.error = "Vui lòng không để trống"
                txtTenLoaiGT.requestFocus()
            }
            else if(txtTrangThai.text.trim().isEmpty()){
                txtTrangThai.error = "Vui lòng không để trống"
                txtTrangThai.requestFocus()
            }
            else if(!txtTenLoaiGT.text.trim().isEmpty() || !txtTrangThai.text.trim().isEmpty()){
                //call api
                viewModel.insertLoaiGT(LoaiGtModel(0, txtTenLoaiGT.text.toString(), txtTrangThai.text.toString()))
                //Livedata observer
                getDataCoroutine("Insert success", "Insert fail")
                txtTenLoaiGT.setText("")
                txtTrangThai.setText("")
                txtTenLoaiGT.requestFocus()
                initViewModel()
                Log.d("TAG", "size new: ${loaiGTs.size}")
            }
            dialog.show()
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun dialogEdit(loaiGt: LoaiGtModel){
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
        val txtTenLoaiGT: EditText = dialog.findViewById(R.id.txtTenLoaiGT)
        val txtTrangThai: EditText = dialog.findViewById(R.id.txtTTLoaiGT)
        var btnThem: Button = dialog.findViewById(R.id.btnThemLoaiGT)
        var btnHuy: Button = dialog.findViewById(R.id.btnHuyLoaiGT)
        txtTenLoaiGT.setText(loaiGt.tenLoaiGT)
        txtTrangThai.setText(loaiGt.trangThai)
        btnThem.setText("Update")
        btnThem.setOnClickListener {
            if(txtTenLoaiGT.text.trim().isEmpty()){
                txtTenLoaiGT.error = "Vui lòng không để trống"
                txtTenLoaiGT.requestFocus()
            }
            else if(txtTrangThai.text.trim().isEmpty()){
                txtTrangThai.error = "Vui lòng không để trống"
                txtTrangThai.requestFocus()
            }
            else if(!txtTenLoaiGT.text.trim().isEmpty() || !txtTrangThai.text.trim().isEmpty()){
                //call api
                viewModel.updateLoaiGT(LoaiGtModel(0, txtTenLoaiGT.text.toString(), txtTrangThai.text.toString()))
                if((txtTenLoaiGT.text.toString() != loaiGt.tenLoaiGT) || (txtTrangThai.text.toString() != loaiGt.trangThai)){
                    //Livedata observer
                    getDataCoroutine("Update success","Update fail")
                    initViewModel()
                    Log.d("TAG", "size new: ${loaiGTs.size}")
                }}
            dialog.show()
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun getDataCoroutine(success: String, fail: String) {
        //Livedata observer
        viewModel.loaiGTs.observe(viewLifecycleOwner) {response ->
            if (response == null) {
                Toast.makeText(activity, fail, Toast.LENGTH_SHORT).show()
                return@observe
            }
            else{
                loaiGtAdapter.loaiGTs = response
                initAdapter()
                loaiGtAdapter.notifyDataSetChanged()
                Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun itemClickEdit(loaiGtModel: LoaiGtModel) {
        dialogEdit(loaiGtModel)
        return
    }

    override fun itemClickDelete(id: Int) {
        viewModel.deleteLoaiGT(id)
        initViewModel()
        Toast.makeText(context, "Delete $id success!", Toast.LENGTH_SHORT).show()
        return
    }
}