package com.gym.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessagingService
import com.gym.R
import com.gym.databinding.FragmentLoginBinding
import com.gym.firebase.NotificationHelper
import com.gym.model.NhanVienModel
import com.gym.model.TaiKhoanModel
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class LoginFragment : FragmentNext(){
    private lateinit var binding: FragmentLoginBinding
    var dsTaiKhoan = ArrayList<TaiKhoanModel>()
    var nhanVien = NhanVienModel()
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
                //pbLoad.visibility = View.VISIBLE
                dialogPopMessage("Logging...",R.drawable.ic_load11)
                lifecycleScope.launchWhenCreated {
                    delay(2000L)
                    if(checkLogin(dsTaiKhoan)){
                        if(SingletonAccount.taiKhoan != null)
                            getNhanVienByMaNV(SingletonAccount.taiKhoan!!.maNV)
                        //--show notificatio----
                        //NotificationHelper(requireContext(),R.drawable.ic_hoadon,"Đăng nhập","Tài khoản ${binding.txtUserLogin.text} đăng nhập thành công").Notification()
                        NotificationHelper(requireContext(),R.drawable.ic_hoadon,"Đăng nhập","Nhân viên ${nhanVien.hoTen} đăng nhập thành công!").Notification()
                        val kh = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        kh.hideSoftInputFromWindow(activity?.currentFocus?.windowToken,0)
                        //Toast.makeText(activity,"Tài khoản ${binding.txtUserLogin.text} đăng nhập thành công",Toast.LENGTH_SHORT).show()
                        //---------------------
                        getFragment(view,R.id.navLoginToHome)
                        Toast.makeText(activity, "Login success!", Toast.LENGTH_SHORT).show()
                        //activity!!.finish()
                    }
                    else{
                        //pbLoad.visibility = View.GONE
                        Toast.makeText(activity, "Login failed!", Toast.LENGTH_SHORT).show()
                        return@launchWhenCreated
                    }
                }
            }
        }
    }
    fun innitViewModels(){
        viewModel.getDSTaiKhoan()
        //live data
        lifecycleScope.launchWhenCreated {
            viewModel.taiKhoans.collect{response ->
                if(response.isNotEmpty()){
                    dsTaiKhoan.addAll(response)
                }
                else{
                    //Toast.makeText(activity, "Load api failed!", Toast.LENGTH_SHORT).show()
                    return@collect
                }
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
    fun getCheckAccLogin(): Boolean{
        var user: String = SingletonAccount.taiKhoan?.maTK.toString()
        var pass: String = SingletonAccount.taiKhoan?.matKhau.toString()
        if(user.isEmpty() || pass.isEmpty()){
            binding.apply {
                pbLoad.visibility = View.GONE
                txtUserLogin.isEnabled = true
                txtPassLogin.isEnabled = true
                return false
            }
        }
        else{
            binding.apply {
                txtUserLogin.apply {
                    isEnabled = false
                    setText(user)
                }
                txtPassLogin.apply {
                    isEnabled = false
                    setText(pass)
                }
                pbLoad.visibility = View.VISIBLE
                return false
            }
        }
    }
    fun getNhanVienByMaNV(maNV: String){
        viewModel.getNhanVien(maNV)
        lifecycleScope.launchWhenCreated {
            viewModel.nhanVien.collect{
                nhanVien = it?: return@collect
            }
        }
    }
}