package com.gym.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.adapter.LoaiGtAdapter
import com.gym.databinding.FragmentDichvuBinding
import com.gym.model.LoaiGTModel
import com.gym.ui.DialogLoad
import com.gym.viewmodel.GymViewModel

class DichVuFragment : Fragment() {
    private lateinit var binding: FragmentDichvuBinding
    private lateinit var viewModel: GymViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDichvuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCallAPI()
    }

    fun initCallAPI(){
        viewModel = ViewModelProvider(this)[GymViewModel::class.java]
        //call api
        viewModel.getDSLoaiGT()
        //Livedata observer
        viewModel.loaiGTs.observe(viewLifecycleOwner, Observer {
            initAdapter(it)
        })
    }
    private fun initAdapter(loaiGTs: List<LoaiGTModel>){
        binding.apply {
            rvLoaiGT.layoutManager = LinearLayoutManager(activity)
            rvLoaiGT.adapter = LoaiGtAdapter(loaiGTs)
        }
    }
}