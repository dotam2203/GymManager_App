package com.gym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gym.databinding.FragmentLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvSignUp1.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }
        binding.tvSignUp2.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }

    }
}