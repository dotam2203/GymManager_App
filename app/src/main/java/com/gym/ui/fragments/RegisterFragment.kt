package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gym.R
import com.gym.databinding.FragmentRegisterBinding
import com.gym.ui.FragmentNext

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  06/07/2022
 */
class RegisterFragment: FragmentNext() {
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvLogin1.setOnClickListener {
                getFragment(view,R.id.navRegisterToLogin)
            }
            tvLogin2.setOnClickListener {
                getFragment(view,R.id.navRegisterToLogin)
            }
        }
    }
}