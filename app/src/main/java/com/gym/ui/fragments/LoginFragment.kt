package com.gym.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.gym.R
import com.gym.databinding.FragmentLoginBinding
import com.gym.model.TaiKhoanModel
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount
import com.gym.ui.viewmodel.ViewModel
import kotlin.reflect.KParameter

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  06/07/2022
 */
class LoginFragment : FragmentNext() {
    private lateinit var binding: FragmentLoginBinding
    var dsTaiKhoan = ArrayList<TaiKhoanModel>()
    val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        innitViewModels()
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
                if(checkLogin(dsTaiKhoan)){
                    getFragment(view,R.id.navLoginToHome)
                    Toast.makeText(activity, "Login success!", Toast.LENGTH_SHORT).show()
                    //activity!!.finish()
                }
                else{
                    Toast.makeText(activity, "Login failed!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
        }
    }
    fun innitViewModels(){
        viewModel.getDSTaiKhoan()
        //live data
        viewModel.taiKhoans.observe(viewLifecycleOwner){response ->
            if(response == null){
                Toast.makeText(activity, "Load api failed!", Toast.LENGTH_SHORT).show()
                return@observe
            }
            else{
                dsTaiKhoan = response as ArrayList<TaiKhoanModel> /* = java.util.ArrayList<com.gym.model.TaiKhoanModel> */
            }
        }
    }
    fun checkLogin(taiKhoans: ArrayList<TaiKhoanModel>): Boolean{
        var user: String = binding.txtUserLogin.text.toString()
        var pass: String = binding.txtPassLogin.text.toString()
        for(i in taiKhoans.indices){
            if(user.trim().isEmpty()){
                binding.txtUserLogin.apply {
                    error = "Vui lòng nhập user!"
                    requestFocus()
                }
                return false
            }
            else if(pass.trim().isEmpty()){
                binding.txtPassLogin.apply {
                    error = "Vui lòng nhập pass!"
                    requestFocus()
                }
                return false
            }
            else if(user == taiKhoans[i].maTK && pass == taiKhoans[i].matKhau){
                SingletonAccount.taiKhoan = taiKhoans[i]
                return true
            }
        }
        return false
    }

}