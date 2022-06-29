package com.gym

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.gym.databinding.ActivityRegisterBinding

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  29/06/2022
 */
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvLogin1.setOnClickListener{
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
        }
        binding.tvLogin2.setOnClickListener{
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
        }
    }
}