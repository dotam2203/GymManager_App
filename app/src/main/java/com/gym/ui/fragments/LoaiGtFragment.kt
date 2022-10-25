package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.*
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
                    binding.checkList.visibility = View.GONE
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
        val dialog = Dialog(requireActivity())
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
        val swStatus: Switch = dialog.findViewById(R.id.swStatus)
        val btnThem: Button = dialog.findViewById(R.id.btnThemLoaiGT)
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyLoaiGT)
        swStatus.visibility = View.GONE
        txtTenLoaiGT.requestFocus()
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
                viewModel.insertLoaiGT(LoaiGtModel(0, replaceString(txtTenLoaiGT.text.toString()), txtTrangThai.text.toString()))
                loaiGtAdapter.notifyDataSetChanged()
                //Livedata observer
                getDataCoroutine("Thêm thành công!", "Thêm thất bại")
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
        val dialog = Dialog(requireActivity())
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
        val swStatus: Switch = dialog.findViewById(R.id.swStatus)
        val btnThem: Button = dialog.findViewById(R.id.btnThemLoaiGT)
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyLoaiGT)
        txtTenLoaiGT.setText(loaiGt.tenLoaiGT)
        txtTenLoaiGT.requestFocus()
        txtTrangThai.setText(loaiGt.trangThai)
        txtTrangThai.isEnabled = false
        btnThem.text = "Cập nhật"
        if(txtTrangThai.text.toString() == "Hoạt động"){
            swStatus.isChecked = true
        }
        else if(txtTrangThai.text.toString() == "Khóa"){
            swStatus.isChecked = false
        }
        swStatus.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                txtTrangThai.setText("Hoạt động")
            }
            else
                txtTrangThai.setText("Khóa")
        }
        btnThem.setOnClickListener {
            val tenLoaiGT: String = txtTenLoaiGT.text.toString()
            if(tenLoaiGT == ""){
                txtTenLoaiGT.error = "Vui lòng không để trống!"
                txtTenLoaiGT.requestFocus()
            }
            viewModel.updateLoaiGT(LoaiGtModel(loaiGt.idLoaiGT, replaceString(txtTenLoaiGT.text.toString()), txtTrangThai.text.toString()))
            getDataCoroutine("Cập nhật thành công","Cập nhật thất bại")
            initViewModel()
            Log.d("TAG", "size new: ${loaiGTs.size}")
            dialog.show()
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun dialogDelete(idLoaiGT: Int){
        val dialog = Dialog(requireActivity())
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
            dialogPopMessage("Xóa loại gói tập ${loaiGT.tenLoaiGT} thành công!",R.drawable.ic_ok)
            dialog.dismiss()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun getDataCoroutine(success: String, fail: String) {
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect {response ->
                if (response.isEmpty()) {
                    dialogPopMessage(fail,R.drawable.ic_fail)
                    return@collect
                }
                else{
                    loaiGtAdapter.loaiGTs.addAll(response)
                    initAdapter()
                    loaiGtAdapter.notifyDataSetChanged()
                    dialogPopMessage(success,R.drawable.ic_ok)
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