package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentGoitapBinding
import com.gym.model.GoiTapModel
import com.gym.ui.adapter.GoiTapAdapter
import com.gym.ui.viewmodel.GoiTapViewModel

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  14/07/2022
 */
class GoiTapFragment: Fragment() {
    private lateinit var binding: FragmentGoitapBinding
    var goiTapAdapter = GoiTapAdapter()
    var goiTaps = ArrayList<GoiTapModel>()
    val viewModel: GoiTapViewModel by lazy {
        ViewModelProvider(this).get(GoiTapViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoitapBinding.inflate(layoutInflater)
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
        viewModel.getDSGoiTap()
        //Livedata observer
        viewModel.goiTaps.observe(viewLifecycleOwner) {response ->
            if (response == null) {
                binding.pbLoad.visibility = View.VISIBLE
                Toast.makeText(activity, "Load api failed!", Toast.LENGTH_SHORT).show()
                return@observe
            }
            else{
                goiTapAdapter.goiTaps = response
                goiTaps = response as ArrayList<GoiTapModel> /* = java.util.ArrayList<com.gym.model.GoiTapModel> */
                initAdapter()
                goiTapAdapter.notifyDataSetChanged()
                binding.pbLoad.visibility = View.GONE
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