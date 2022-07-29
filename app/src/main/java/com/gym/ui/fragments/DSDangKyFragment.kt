package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gym.databinding.FragmentDsDangkyBinding

class DSDangKyFragment : Fragment() {
    private lateinit var binding: FragmentDsDangkyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDsDangkyBinding.inflate(layoutInflater)
        return binding.root
    }
}