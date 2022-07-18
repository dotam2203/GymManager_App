package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentKhachhangBinding
import com.gym.model.KhachHangModel
import com.gym.ui.adapter.KhachHangAdapter
import com.gym.ui.viewmodel.KhachHangViewModel

class KhachHangFragment : Fragment() {
    private lateinit var binding: FragmentKhachhangBinding
    var khachHangAdapter = KhachHangAdapter()
    var khachHangs = ArrayList<KhachHangModel>()
    val viewModel: KhachHangViewModel by lazy {
        ViewModelProvider(this).get(KhachHangViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKhachhangBinding.inflate(layoutInflater)
        initViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*binding.apply {
            imbAdd.setOnClickListener {
                dialog()
            }
        }*/
    }
    fun initViewModel() {
        //call api
        viewModel.getDSKhachHang()
        //Livedata observer
        viewModel.khachHangs.observe(viewLifecycleOwner) {response ->
            if (response == null) {
                binding.pbLoad.visibility = View.VISIBLE
                Toast.makeText(activity, "Load api failed!", Toast.LENGTH_SHORT).show()
                return@observe
            }
            else{
                khachHangAdapter.khachHangs = response
                khachHangs = response as ArrayList<KhachHangModel> /* = java.util.ArrayList<com.gym.model.KhachHangModel> */
                initAdapter()
                khachHangAdapter.notifyDataSetChanged()
                binding.pbLoad.visibility = View.GONE
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvKhachHang.apply {
                layoutManager = LinearLayoutManager(activity!!)
                adapter = khachHangAdapter
            }
            //loaiGtAdapter.updateData(loaiGTs)
            imbAdd.setOnClickListener {
                //dialogInsert()
            }
        }
    }

    fun dialog(){
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
        /*var btnHuy: Button = dialog.findViewById(R.id.btnHuy)
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }*/
    }
}