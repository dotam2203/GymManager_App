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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentLoaigtBinding
import com.gym.model.LoaiGtModel
import com.gym.ui.adapter.LoaiGtAdapter
import com.gym.ui.viewmodel.ViewModel

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  14/07/2022
 */
class LoaiGtFragment : Fragment(), LoaiGtAdapter.OnItemClick {
    private lateinit var binding: FragmentLoaigtBinding
    var loaiGtAdapter = LoaiGtAdapter(this@LoaiGtFragment)
    var loaiGTs = ArrayList<LoaiGtModel>()
    val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoaigtBinding.inflate(layoutInflater)
        refreshData()
        initViewModel()
        return binding.root
    }

    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                initViewModel()
                refresh.isRefreshing = false
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imbAdd.setOnClickListener {
            dialogInsert()
        }
    }

    fun initViewModel() {
        //call api
        viewModel.getDSLoaiGT()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect {response ->
                if (response.isEmpty()) {
                    binding.pbLoad.visibility = View.GONE
                    Toast.makeText(activity, "List null!", Toast.LENGTH_SHORT).show()
                    return@collect
                }
                else{
                    initAdapter()
                    loaiGtAdapter.loaiGTs = response
                    loaiGTs.addAll(response)
                    loaiGtAdapter.notifyDataSetChanged()
                    binding.pbLoad.visibility = View.GONE
                }
            }

        }
    }

    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvLoaiGT.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = loaiGtAdapter
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
        txtTrangThai.isEnabled = false
        btnThem.setOnClickListener {
            if(txtTenLoaiGT.text.trim().isEmpty()){
                txtTenLoaiGT.error = "Vui lòng không để trống!"
                txtTenLoaiGT.requestFocus()
            }
            else if(txtTrangThai.text.trim().isEmpty()){
                txtTrangThai.error = "Vui lòng không để trống!"
                txtTrangThai.requestFocus()
            }
            else if(!txtTenLoaiGT.text.trim().isEmpty() || !txtTrangThai.text.trim().isEmpty()){
                //call api
                viewModel.insertLoaiGT(LoaiGtModel(0, txtTenLoaiGT.text.toString(), txtTrangThai.text.toString()))
                //Livedata observer
                getDataCoroutine("Insert success", "Insert fail")
                initViewModel()
                txtTenLoaiGT.setText("")
                txtTrangThai.setText("")
                txtTenLoaiGT.requestFocus()
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
        txtTrangThai.isEnabled = true
        btnThem.setText("Update")
        btnThem.setOnClickListener {
            val tenLoaiGT: String = txtTenLoaiGT.text.toString()
            val trangThai: String = txtTrangThai.text.toString()
            if(tenLoaiGT.equals("")){
                txtTenLoaiGT.error = "Vui lòng không để trống!"
                txtTenLoaiGT.requestFocus()
            }
            if(trangThai.equals("")){
                txtTrangThai.error = "Vui lòng không để trống!"
                txtTrangThai.requestFocus()
            }
            //call api
            viewModel.updateLoaiGT(LoaiGtModel(loaiGt.idLoaiGT, txtTenLoaiGT.text.toString(), txtTrangThai.text.toString()))
            //Livedata observer
            getDataCoroutine("Update success","Update fail")
            initViewModel()
            Log.d("TAG", "size new: ${loaiGTs.size}")
            dialog.show()
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun getDataCoroutine(success: String, fail: String) {
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect {response ->
                if (response.isEmpty()) {
                    Toast.makeText(activity, fail, Toast.LENGTH_SHORT).show()
                    return@collect
                }
                else{
                    loaiGtAdapter.loaiGTs = response
                    initAdapter()
                    loaiGtAdapter.notifyDataSetChanged()
                    Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun itemClickEdit(loaiGtModel: LoaiGtModel) {
        dialogEdit(loaiGtModel)
        return
    }

    override fun itemClickDelete(id: Int) {
        viewModel.deleteLoaiGT(id)
        getDataCoroutine("Delete successed!", "Delete failed!")
        initViewModel()
        return
    }
}