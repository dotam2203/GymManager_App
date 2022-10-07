package com.gym.ui.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.gym.R
import com.gym.databinding.FragmentLoginBinding
import com.gym.firebase.NotificationHelper
import com.gym.model.NhanVienModel
import com.gym.model.TaiKhoanModel
import com.gym.ui.FragmentNext
import com.gym.ui.SharedPreferencesLogin
import com.gym.ui.SingletonAccount
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList

class LoginFragment : FragmentNext() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private lateinit var username: String
    private lateinit var password: String

    var dsTaiKhoan = ArrayList<TaiKhoanModel>()
    var emails = ArrayList<String>()
    var nhanVien = NhanVienModel()
    var taiKhoan = TaiKhoanModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        Log.e("TAGG", "user login: ${SingletonAccount.taiKhoan?.maTK}")
        dialogPopMessage("Loading...", R.drawable.ic_load11)
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            init()
            checkAutoLogin()
        }
        innitViewModels()
        return binding.root
    }

    private fun init() {
        sharedPreferencesLogin = SharedPreferencesLogin(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvSignUp1.setOnClickListener {
                getFragment(view, R.id.navLoginToRegister)
            }
            tvSignUp2.setOnClickListener {
                getFragment(view, R.id.navLoginToRegister)
            }
            btnLogin.setOnClickListener {
                dialogPopMessage("Loading...", R.drawable.ic_load11)
                lifecycleScope.launchWhenCreated {
                    delay(2000L)
                    if (checkLogin(dsTaiKhoan)) {
                        for (i in dsTaiKhoan.indices) {
                            if (txtUserLogin.text.toString().trim() == dsTaiKhoan[i].maTK) {
                                getNhanVienByMaNV(dsTaiKhoan[i].maNV)
                                lifecycleScope.launchWhenCreated {
                                    delay(2000L)
                                    //====================
                                    NotificationHelper(requireContext(), R.drawable.ic_hoadon, "Đăng nhập", "Nhân viên ${nhanVien.hoTen} đăng nhập thành công!").Notification()
                                    val kh = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                    kh.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
                                    //===============================
                                }
                                break
                            }
                        }
                        //---------------------
                        getFragment(view, R.id.navLoginToHome)
                        Toast.makeText(activity, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show()
                        txtPassLogin.setText("")
                    }
                }
            }
            tvForgotPass.setOnClickListener {
                tvForgotPass.setTextColor(R.color.red)
                getFragment(view,R.id.navLoginToForgotFragment)
            }
        }
    }
    private fun innitViewModels() {
        viewModel.getDSTaiKhoan()
        //live data
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

    fun checkAutoLogin() {
        if (sharedPreferencesLogin.getUser() != null) {
            binding.apply {
                txtUserLogin.setText(sharedPreferencesLogin.getUser())
                txtPassLogin.setText(sharedPreferencesLogin.getPass())
            }
            if (checkLogin(dsTaiKhoan)) {
                for (i in dsTaiKhoan.indices) {
                    if (binding.txtUserLogin.text.toString().trim() == dsTaiKhoan[i].maTK) {
                        nhanVien = getNhanVienByMaNV(dsTaiKhoan[i].maNV)
                        lifecycleScope.launchWhenCreated {
                            delay(2000L)
                            //====================
                            NotificationHelper(requireContext(), R.drawable.ic_hoadon, "Đăng nhập", "Nhân viên ${nhanVien.hoTen} đăng nhập thành công!").Notification()
                            val kh = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            kh.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
                            //===============================
                        }
                        break
                    }
                }
                Log.e("PassSavePrefe", "checkAutoLogin: ${sharedPreferencesLogin.getPass()}")
                Log.e("PassSaveSingleton", "checkAutoLogin: ${SingletonAccount.taiKhoan?.matKhau}")
                getFragment(requireView(), R.id.navLoginToHome)
            }
        }
    }

    fun checkLogin(taiKhoans: ArrayList<TaiKhoanModel>): Boolean {
        val user: String = binding.txtUserLogin.text.toString()
        val pass: String = maHoaPassApi(binding.txtPassLogin.text.toString())
        for (i in taiKhoans.indices) {
            if (user.trim().isEmpty()) {
                binding.txtUserLogin.apply {
                    error = "Vui lòng nhập user!"
                    requestFocus()
                }
                return false
            } else if (pass.trim().isEmpty()) {
                binding.txtPassLogin.apply {
                    error = "Vui lòng nhập pass!"
                    requestFocus()
                }
                return false
            } else if (user == taiKhoans[i].maTK && pass == taiKhoans[i].matKhau) {
                SingletonAccount.taiKhoan = taiKhoans[i]
                sharedPreferencesLogin.apply {
                    setUser(user)
                    setPass(binding.txtPassLogin.text.toString())
                }
                return true
            }
        }
        return false
    }

    fun getCheckAccLogin(): Boolean {
        val user: String = SingletonAccount.taiKhoan?.maTK.toString()
        val pass: String = SingletonAccount.taiKhoan?.matKhau.toString()
        if (user.isEmpty() || pass.isEmpty()) {
            binding.apply {
                pbLoad.visibility = View.GONE
                txtUserLogin.isEnabled = true
                txtPassLogin.isEnabled = true
                return false
            }
        } else {
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
}