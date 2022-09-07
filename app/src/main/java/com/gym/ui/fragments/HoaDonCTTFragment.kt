package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gym.R
import com.gym.databinding.FragmentHoadonCttBinding
import kotlinx.coroutines.delay

class HoaDonCTTFragment : Fragment() {
    private lateinit var binding: FragmentHoadonCttBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHoadonCttBinding.inflate(layoutInflater)
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            lifecycleScope.launchWhenCreated {
                delay(1000L)
                pbLoad.visibility = View.GONE
                checkList.visibility = View.VISIBLE
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}