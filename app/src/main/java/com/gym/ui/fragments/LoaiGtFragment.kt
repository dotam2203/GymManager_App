package com.gym.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
class LoaiGtFragment : Fragment() {
    private lateinit var binding: FragmentLoaigtBinding
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
        viewModel.loaiGTs.observe(viewLifecycleOwner) { response ->
            if (response == null) {
                binding.pbLoad.visibility = View.VISIBLE
                Toast.makeText(activity, "Load api failed!", Toast.LENGTH_SHORT).show()
                return@observe
            }
            initAdapter(response)
            binding.pbLoad.visibility = View.GONE
        }
    }

    private fun initAdapter(loaiGTs: List<LoaiGtModel>) {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvLoaiGT.layoutManager = LinearLayoutManager(activity)
            rvLoaiGT.adapter = LoaiGtAdapter(loaiGTs)
            LoaiGtAdapter(loaiGTs).notifyDataSetChanged()
            imbAdd.setOnClickListener {
//                dialogAddLoaiGT()
                dialog()
            }
        }
    }
    fun dialog(){
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
        var btnHuy: Button = dialog.findViewById(R.id.btnHuyLoaiGT)
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }
}