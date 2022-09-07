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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentLoaigtBinding
import com.gym.model.LoaiGtModel
import com.gym.model.LoaiKhModel
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount
import com.gym.ui.adapter.LoaiGtAdapter
import kotlinx.coroutines.delay

class LoaiGtFragment : FragmentNext(), LoaiGtAdapter.OnItemClick {
    private lateinit var binding: FragmentLoaigtBinding
    var loaiGtAdapter = LoaiGtAdapter(this@LoaiGtFragment)
    var loaiGTs = ArrayList<LoaiGtModel>()
    var loaiGT = LoaiGtModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoaigtBinding.inflate(layoutInflater)
        initAdapter()
        refreshData()
        if(SingletonAccount.taiKhoan?.maQuyen == "Q02"){
            binding.imbAdd.visibility = View.GONE
        }
        binding.pbLoad.visibility = View.VISIBLE
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            initViewModel()
        }

        return binding.root
    }
    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                loaiGtAdapter.notifyDataSetChanged()
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

    private fun initViewModel() {
        //call api
        viewModel.getDSLoaiGT()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect {response ->
                if(response.isNotEmpty()){
                    loaiGtAdapter.loaiGTs.clear()
                    loaiGtAdapter.loaiGTs.addAll(response)
                    loaiGTs.addAll(response)
                    loaiGtAdapter.notifyDataSetChanged()
                    binding.pbLoad.visibility = View.GONE
                }
                else {
                    binding.checkList.visibility = View.VISIBLE
                    binding.pbLoad.visibility = View.GONE
                    loaiGtAdapter.loaiGTs.clear()
                    loaiGtAdapter.notifyDataSetChanged()
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
    private fun dialogInsert(){
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
                loaiGtAdapter.notifyDataSetChanged()
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
        txtTenLoaiGT.requestFocus()
        txtTrangThai.setText(loaiGt.trangThai)
        txtTrangThai.isEnabled = true
        btnThem.setText("Cập nhật")
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
    private fun dialogDelete(idLoaiGT: Int){
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_message)

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
        val title: TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)
        //-------------------------------
        loaiGT = getLoaiGTByID(idLoaiGT)
        //-------------------------------
        title.text = "Bạn thật sự muốn xóa loại gói tập ${loaiGT.tenLoaiGT}?"
        btnYes.setOnClickListener {
            viewModel.deleteLoaiGT(idLoaiGT)
            Toast.makeText(requireContext(), "Xóa loại gói tập ${loaiGT.tenLoaiGT} thành công!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun getLoaiGTByID(idLoaiGT: Int): LoaiGtModel {
        var loaiGT = LoaiGtModel()
        viewModel.getLoaiGT(idLoaiGT)
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGT.collect{
                if(it != null){
                    loaiGT = it
                }
                else
                    return@collect
            }
        }
        return loaiGT
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
                    loaiGtAdapter.loaiGTs.addAll(response)
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
        dialogDelete(id)
    }
}