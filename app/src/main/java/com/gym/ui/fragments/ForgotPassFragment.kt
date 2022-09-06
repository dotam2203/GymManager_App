package com.gym.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.gym.R
import com.gym.databinding.FragmentForgotPassBinding
import com.gym.model.NhanVienModel
import com.gym.model.TaiKhoanModel
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount
import kotlinx.coroutines.delay
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

class ForgotPassFragment : FragmentNext() {
    private lateinit var binding: FragmentForgotPassBinding

    var dsTaiKhoan = ArrayList<TaiKhoanModel>()
    var taiKhoan = TaiKhoanModel()
    var nhanVien = NhanVienModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPassBinding.inflate(layoutInflater)
        innitViewModels()
        getEvent()
        return binding.root
    }


    private fun innitViewModels() {
        viewModel.getDSTaiKhoan()
        lifecycleScope.launchWhenCreated {
            viewModel.taiKhoans.collect { response ->
                if (response.isNotEmpty()) {
                    dsTaiKhoan.addAll(response)
                } else {
                    return@collect
                }
            }
        }
    }

    private fun getEvent() {
        binding.apply {
            txtForgotPass.requestFocus()
            txtForgotPass.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (Patterns.EMAIL_ADDRESS.matcher(txtForgotPass.text.toString()).matches())
                        btnForgot.isEnabled = true
                    else {
                        btnForgot.isEnabled = false
                        txtForgotPass.error = "Sai định dạng email!"
                    }
                }

            })
            btnForgot.setOnClickListener {
                val email = txtForgotPass.text.toString().trim()
                val newPass = createPassword()
                if(checkEmail(email)){
                    getTaiKhoanByEmail(email)
                    getNhanVienByMaNV(taiKhoan.maNV)
                    val taiKhoanModel = TaiKhoanModel(taiKhoan.maTK,maHoaPassApi(newPass),taiKhoan.trangThai,taiKhoan.maQuyen,taiKhoan.maNV)
                    viewModel.updateTaiKhoan(taiKhoanModel)
                    if(SingletonAccount.taiKhoan != null){
                        SingletonAccount.taiKhoan = taiKhoanModel
                    }
                    else if(SingletonAccount.taiKhoan == null){
                        SingletonAccount.taiKhoan = taiKhoanModel
                    }
                    //==========
                    val title = "VECTOR GYM - CẤP MẬT KHẨU MỚI CHO TÀI KHOẢN ĐĂNG NHẬP"
                    val text = "Mật khẩu mới đã được gửi!"
                    val message = "THÔNG TIN TÀI KHOẢN\nEmail:${taiKhoan.email}\nTÀI KHOẢN ĐĂNG NHẬP: ${taiKhoan.maTK}\nMẬT KHẨU MỚI: ${newPass}\n\n\nCẢM ƠN bạn đã đồng hành cùng VECTOR GYM!"
                    //==========
                    sendMessageFromMail(email,title,message,text)
                    lifecycleScope.launchWhenCreated {
                        delay(4000L)
                        getFragment(requireView(),R.id.navForgotPassToLogin)
                    }
                    //txtForgotPass.setText("")
                    Log.e("TAGG", "Mật khẩu cấp mới tài khoản ${taiKhoan.maTK}: $newPass")
                }
                else{
                    Toast.makeText(requireContext(), "Tài khoản chưa được đăng ký.\nVui lòng đăng ký tài khoản!", Toast.LENGTH_LONG).show()
                    txtForgotPass.setText("")
                }

            }
            imvClose.setOnClickListener {
                imvClose.setBackgroundColor(R.color.blue)
                getFragment(requireView(),R.id.navForgotPassToLogin)
            }
        }
    }
    private fun getNhanVienByMaNV(maNV: String) {
        viewModel.getNhanVien(maNV)
        lifecycleScope.launchWhenCreated {
            viewModel.nhanVien.collect {
                nhanVien = it ?: return@collect
            }
        }
    }
    private fun getTaiKhoanByEmail(email: String){
        for(i in dsTaiKhoan.indices){
            if(email == dsTaiKhoan[i].email){
                taiKhoan = dsTaiKhoan[i]
                break
            }
        }
    }
    private fun checkEmail(email: String): Boolean{
        for(i in dsTaiKhoan.indices){
            if(email == dsTaiKhoan[i].email)
                return true
        }
        return false
    }
}