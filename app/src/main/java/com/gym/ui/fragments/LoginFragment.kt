package com.gym.ui.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide.init
import com.google.firebase.auth.FirebaseAuth
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

class LoginFragment : FragmentNext() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private lateinit var username: String
    private lateinit var password: String

    var dsTaiKhoan = ArrayList<TaiKhoanModel>()
    var nhanVien = NhanVienModel()

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
                        Toast.makeText(activity, "Login success!", Toast.LENGTH_SHORT).show()
                        //activity?.finish()
                    } else {
                        //pbLoad.visibility = View.GONE
                        Toast.makeText(activity, "Login failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            tvForgotPass.setOnClickListener {
                tvForgotPass.setTextColor(R.color.red)
                dialogForgotPass()
            }
        }
    }

    private fun dialogForgotPass() {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_forgot_pass)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes: WindowManager.LayoutParams = window!!.attributes
        windowAtrributes.gravity = Gravity.CENTER
        window.attributes = windowAtrributes
        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        dialog.setCancelable(false)
        dialog.show()
        val imvClose: CircleImageView = dialog.findViewById(R.id.imvClose)
        val txtForgotPass: EditText = dialog.findViewById(R.id.txtForgotPass)
        val btnForgot: Button = dialog.findViewById(R.id.btnForgot)
        txtForgotPass.requestFocus()
        txtForgotPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(txtForgotPass.text.toString()).matches())
                    btnForgot.isEnabled = true
                else {
                    btnForgot.isEnabled = false
                    txtForgotPass.error = "Sai định dạng email!"
                }
            }

        })
        btnForgot.setOnClickListener {
            val email = txtForgotPass.text.toString()
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Mật khẩu mới đã được gửi về Email của bạn!", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                        binding.txtPassLogin.setText("")
                    } else {
                        Toast.makeText(requireContext(), task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }
        imvClose.setOnClickListener {
            imvClose.setBackgroundColor(R.color.blue)
            dialog.dismiss()
        }
    }

    fun innitViewModels() {
        viewModel.getDSTaiKhoan()
        //live data
        lifecycleScope.launchWhenCreated {
            viewModel.taiKhoans.collect { response ->
                if (response.isNotEmpty()) {
                    dsTaiKhoan.addAll(response)
                } else {
                    //Toast.makeText(activity, "Load api failed!", Toast.LENGTH_SHORT).show()
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
                getFragment(requireView(), R.id.navLoginToHome)
            }
            //activity?.finish()
        }
    }

    fun checkLogin(taiKhoans: ArrayList<TaiKhoanModel>): Boolean {
        var user: String = binding.txtUserLogin.text.toString()
        var pass: String = binding.txtPassLogin.text.toString()
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
                    setPass(pass)
                }
                return true
            }
        }
        return false
    }

    fun getCheckAccLogin(): Boolean {
        var user: String = SingletonAccount.taiKhoan?.maTK.toString()
        var pass: String = SingletonAccount.taiKhoan?.matKhau.toString()
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

    fun getNhanVienByMaNV(maNV: String) {
        viewModel.getNhanVien(maNV)
        lifecycleScope.launchWhenCreated {
            viewModel.nhanVien.collect {
                nhanVien = it ?: return@collect
            }
        }
    }

    fun getTaiKhoanByMaTK(maTK: String): TaiKhoanModel {
        var tk = TaiKhoanModel()
        viewModel.getTaiKhoan(maTK)
        lifecycleScope.launchWhenCreated {
            viewModel.taiKhoan.collect {
                tk = it ?: return@collect
            }
        }
        return tk
    }
}