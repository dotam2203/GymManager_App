package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.gym.databinding.FragmentDangkyBinding
import com.gym.ui.FragmentNext
import com.gym.ui.viewmodel.ViewModel

class DangKyFragment : FragmentNext() {
    private lateinit var binding: FragmentDangkyBinding
    val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDangkyBinding.inflate(layoutInflater)
        binding.txtNgayBD.setText(getCurrentDate())
        getControl()
        return binding.root
    }

    private fun getControl() {
        binding.apply {
            imbCalendar.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                val support = requireActivity().supportFragmentManager
                //receive date
                support.setFragmentResultListener("REQUEST_KEY", viewLifecycleOwner){
                    resultKey, bundle ->
                    if(resultKey == "REQUEST_KEY"){
                        val date = bundle.getString("PASS_DATE")
                        txtNgayBD.setText(date)
                    }
                }
                //show dialog
                datePickerFragment.show(support,"DatePickerFragment")
            }
        }
    }

    fun getTenGoiTap(){
    }
}