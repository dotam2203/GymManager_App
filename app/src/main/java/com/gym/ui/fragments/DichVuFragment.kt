package com.gym.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.ui.adapter.LoaiGtAdapter
import com.gym.databinding.FragmentDichvuBinding
import com.gym.model.LoaiGtModel
import com.gym.ui.viewmodel.LoaiGtViewModel

class DichVuFragment : Fragment() {
    private lateinit var binding: FragmentDichvuBinding
    val viewModel: LoaiGtViewModel by lazy {
        ViewModelProvider(this).get(LoaiGtViewModel::class.java)
    }
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
        //call api
        viewModel.getDSLoaiGT()
        //Livedata observer
        viewModel.loaiGTs.observe(viewLifecycleOwner){ response ->
            if(response == null){
                Toast.makeText(activity, "Load api failed!", Toast.LENGTH_SHORT).show()
                return@observe
            }
            initAdapter(response)
        }
    }
    private fun initAdapter(loaiGTs: List<LoaiGtModel>){
        binding.apply {
            rvLoaiGT.layoutManager = LinearLayoutManager(activity)
            rvLoaiGT.adapter = LoaiGtAdapter(loaiGTs)
        }
    }
}