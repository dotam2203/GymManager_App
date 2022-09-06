package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.common.api.internal.LifecycleCallback.getFragment
import com.gym.R
import com.gym.databinding.FragmentSlideBinding
import com.gym.databinding.TessssBinding
import com.gym.ui.FragmentNext
import kotlinx.coroutines.delay

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  06/07/2022
 */
class SlideFragment: FragmentNext() {
    private lateinit var binding: FragmentSlideBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSlideBinding.inflate(layoutInflater)
        lifecycleScope.launchWhenCreated {
            delay(3000L)
            getFragment(requireView(), R.id.navSlideToLogin)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
