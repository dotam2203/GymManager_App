package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gym.databinding.FragmentDangkyBinding
import com.gym.ui.FragmentNext

class DangKyFragment : FragmentNext() {
    private lateinit var binding: FragmentDangkyBinding
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
}