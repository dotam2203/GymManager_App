package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gym.R
import com.gym.databinding.FragmentLoginBinding
import com.gym.ui.FragmentNext

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  06/07/2022
 */
class LoginFragment : FragmentNext() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvSignUp1.setOnClickListener {
                getFragment(view,R.id.navLoginToRegister)
            }
            tvSignUp2.setOnClickListener {
                getFragment(view,R.id.navLoginToRegister)
            }
            btnLogin.setOnClickListener {
                getFragment(view,R.id.navLoginToHome)
            }
        }
    }

}