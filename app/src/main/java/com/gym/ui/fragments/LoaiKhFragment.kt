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
import com.google.android.material.textfield.TextInputLayout
import com.gym.R
import com.gym.databinding.FragmentLoaikhBinding
import com.gym.model.KhachHangModel
import com.gym.model.LoaiKhModel
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.LoaiKhAdapter
import kotlinx.coroutines.delay

class LoaiKhFragment : FragmentNext(), LoaiKhAdapter.OnItemClick {
    private lateinit var binding: FragmentLoaikhBinding
    var loaiKhAdapter = LoaiKhAdapter(this@LoaiKhFragment)
    var loaiKHs = ArrayList<LoaiKhModel>()
    var loaiKH = LoaiKhModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoaikhBinding.inflate(layoutInflater)
        binding.pbLoad.visibility = View.VISIBLE
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            initViewModel()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshData()
        binding.imbAdd.setOnClickListener {
            dialogInsert()
        }
    }

    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                initViewModel()
                refresh.isRefreshing = false
            }

        }
    }

    fun initViewModel() {
        //call api
        viewModel.getDSLoaiKH()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.loaiKHs.collect { response ->
                if (response.isEmpty()) {
                    binding.pbLoad.visibility = View.GONE
                    Toast.makeText(activity, "List null!", Toast.LENGTH_SHORT).show()
                    return@collect
                } else {
                    loaiKhAdapter.loaiKHs = response
                    loaiKHs.addAll(response)
                    initAdapter()
                    loaiKhAdapter.notifyDataSetChanged()
                    binding.pbLoad.visibility = View.GONE
                }
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
        }
    }

    fun dialogInsert() {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_loaigt)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes: WindowManager.LayoutParams = window!!.attributes
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
            if (txtTenLoaiKH.text.trim().isEmpty()) {
                txtTenLoaiKH.error = "Vui lòng không để trống"
                txtTenLoaiKH.requestFocus()
            } else {
                //call api
                viewModel.insertLoaiKH(LoaiKhModel(0, txtTenLoaiKH.text.toString()))
                Toast.makeText(requireActivity(), "Thêm loại gt thành công!", Toast.LENGTH_SHORT).show()
                txtTenLoaiKH.setText("")
                txtTenLoaiKH.requestFocus()
                Log.d("TAG", "size new: ${loaiKHs.size}")
            }
            dialog.show()
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun dialogEdit(loaiKh: LoaiKhModel) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_loaigt)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes: WindowManager.LayoutParams = window!!.attributes
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
        txtTenLoaiKH.requestFocus()
        btnThem.setText("Cập nhật")
        btnThem.setOnClickListener {
            if (txtTenLoaiKH.text.trim().isEmpty()) {
                txtTenLoaiKH.error = "Vui lòng không để trống"
                txtTenLoaiKH.requestFocus()
            } else {
                //call api
                viewModel.updateLoaiKH(LoaiKhModel(loaiKh.idLoaiKH, txtTenLoaiKH.text.toString()))
                Toast.makeText(requireActivity(), "Cập nhật loại khách hàng thành công!", Toast.LENGTH_SHORT).show()
                dialog.show()
            }
            btnHuy.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
    private fun dialogDelete(idLoaiKH: Int){
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
        loaiKH = getLoaiKHByID(idLoaiKH)
        //-------------------------------
        title.text = "Bạn thật sự muốn xóa loại khách hàng ${loaiKH.tenLoaiKH}?"
        btnYes.setOnClickListener {
            viewModel.deleteLoaiKH(idLoaiKH)
            Toast.makeText(requireContext(), "Xóa loại khách hàng ${loaiKH.tenLoaiKH} thành công!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun getLoaiKHByID(idLoaiKH: Int): LoaiKhModel {
        var loaiKH = LoaiKhModel()
        viewModel.getLoaiKH(idLoaiKH)
        lifecycleScope.launchWhenCreated {
            viewModel.loaiKH.collect{
                if(it != null){
                    loaiKH = it
                }
                else
                    return@collect
            }
        }
        return loaiKH
    }
    private fun getDataCoroutine(success: String, fail: String) {
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.loaiKHs.collect { response ->
                if (response.isEmpty()) {
                    Toast.makeText(activity, fail, Toast.LENGTH_SHORT).show()
                    return@collect
                } else {
                    loaiKhAdapter.loaiKHs = response
                    initAdapter()
                    loaiKhAdapter.notifyDataSetChanged()
                    Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun itemClickEdit(loaiKhModel: LoaiKhModel) {
        dialogEdit(loaiKhModel)
        return
    }

    override fun itemClickDelete(id: Int) {
        dialogDelete(id)
    }
}