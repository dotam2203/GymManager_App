package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.gym.R
import com.gym.databinding.FragmentLoaikhBinding
import com.gym.model.KhachHangModel
import com.gym.model.LoaiKhModel
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount
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
        initAdapter()
        refreshData()
        if(SingletonAccount.taiKhoan?.maQuyen == "Q02"){
            binding.imbAdd.visibility = View.GONE
        }
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            initViewModel()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imbAdd.setOnClickListener {
            dialogInsert()
        }
    }

    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                loaiKhAdapter.notifyDataSetChanged()
                refresh.isRefreshing = false
                pbLoad.visibility = View.GONE
            }
        }
    }

    private fun initViewModel() {
        //call api
        viewModel.getDSLoaiKH()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.loaiKHs.collect { response ->
                if (response.isNotEmpty()) {
                    loaiKhAdapter.loaiKHs.clear()
                    loaiKhAdapter.loaiKHs.addAll(response)
                    loaiKHs.addAll(response)
                    loaiKhAdapter.notifyDataSetChanged()
                    binding.checkList.visibility = View.GONE
                    binding.pbLoad.visibility = View.GONE
                }
                else {
                    binding.checkList.visibility = View.VISIBLE
                    binding.pbLoad.visibility = View.GONE
                    loaiKhAdapter.loaiKHs.clear()
                    loaiKhAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvLoaiKH.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = loaiKhAdapter
            }
        }
    }

    fun dialogInsert() {
        val dialog = Dialog(requireActivity())
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
        val swStatus: Switch = dialog.findViewById(R.id.swStatus)
        swStatus.visibility = View.GONE
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
        val dialog = Dialog(requireActivity())
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
        val swStatus: Switch = dialog.findViewById(R.id.swStatus)
        swStatus.visibility = View.GONE
        tvTitle.text = "Loại khách hàng"
        tvTenLoaiKH.hint = "Loại khách hàng"
        tvTrangThai.visibility = View.GONE
        txtTrangThai.visibility = View.GONE
        var btnThem: Button = dialog.findViewById(R.id.btnThemLoaiGT)
        var btnHuy: Button = dialog.findViewById(R.id.btnHuyLoaiGT)
        txtTenLoaiKH.setText(loaiKh.tenLoaiKH)
        txtTenLoaiKH.requestFocus()
        btnThem.text = "Cập nhật"
        btnThem.setOnClickListener {
            if (txtTenLoaiKH.text.trim().isEmpty()) {
                txtTenLoaiKH.error = "Vui lòng không để trống!"
                txtTenLoaiKH.requestFocus()
            }
            viewModel.updateLoaiKH(LoaiKhModel(loaiKh.idLoaiKH, txtTenLoaiKH.text.toString()))
            dialogPopMessage("Cập nhật loại khách hàng thành công!",R.drawable.ic_ok)
            dialog.show()
            btnHuy.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
    private fun dialogDelete(idLoaiKH: Int){
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
        loaiKH = getLoaiKHByID(idLoaiKH)
        //-------------------------------
        title.text = "Bạn thật sự muốn xóa loại khách hàng ${loaiKH.tenLoaiKH}?"
        btnYes.setOnClickListener {
            viewModel.deleteLoaiKH(idLoaiKH)
            dialogPopMessage("Xóa loại khách hàng ${loaiKH.tenLoaiKH} thành công!",R.drawable.ic_ok)
            dialog.dismiss()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
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