package com.gym.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gym.R
import com.gym.databinding.FragmentThetapBinding
import com.gym.model.KhachHangModel
import com.gym.ui.viewmodel.ViewModel

class TheTapFragment : Fragment() {
    private lateinit var binding: FragmentThetapBinding
    val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }
    var khachHangs = ArrayList<KhachHangModel>()
    var phones = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThetapBinding.inflate(layoutInflater)
        initVewModel()
        setControl()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    fun initVewModel(){
        viewModel.getDSKhachHang()
        lifecycleScope.launchWhenCreated {
            viewModel.khachHangs.collect{ response ->
                if(response.isEmpty()){
                    return@collect
                }
                else{
                    khachHangs.addAll(response)
                    for(i in response.indices){
                        phones.add(response[i].sdt)
                    }
                }
            }
        }
    }
    fun setControl(){
        binding.apply {
            var arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,phones)
            txtSearchKH.setAdapter(arrayAdapter)
            btnSearch.setOnClickListener {
                //if()
            }
        }
    }
}