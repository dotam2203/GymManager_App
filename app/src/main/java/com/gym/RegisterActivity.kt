package com.gym

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gym.databinding.FragmentRegisterBinding

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  29/06/2022
 */
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : FragmentRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvLogin1.setOnClickListener{
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
        }
        binding.tvLogin2.setOnClickListener{
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
        }
    }
}