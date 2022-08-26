package com.gym.ui.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
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
import androidx.lifecycle.lifecycleScope
import com.gym.R
import com.gym.databinding.FragmentLoginBinding
import com.gym.firebase.NotificationHelper
import com.gym.model.NhanVienModel
import com.gym.model.TaiKhoanModel
import com.gym.ui.FragmentNext
import com.gym.ui.SharedPreferencesLogin
import com.gym.ui.SingletonAccount
import com.sun.mail.util.ASCIIUtility.getBytes
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.delay
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.collections.ArrayList

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
                if (Patterns.EMAIL_ADDRESS.matcher(txtForgotPass.text.toString()).matches())
                    btnForgot.isEnabled = true
                else {
                    btnForgot.isEnabled = false
                    txtForgotPass.error = "Sai định dạng email!"
                }
            }

        })
        btnForgot.setOnClickListener {
            resetPassByEmail(txtForgotPass.text.toString().trim(),createPassword())
        }
        imvClose.setOnClickListener {
            imvClose.setBackgroundColor(R.color.blue)
            dialog.dismiss()
        }
    }

    private fun resetPassByEmail(email: String, newPass: String) {
        //pass: zrvpdagswfzllgmr
        val toMail = "dolethanhtam1022@gmail.com"
        val toPass = "zrvpdagswfzllgmr"

        val host = "smtp.gmail.com"
        val properties = System.getProperties()
        properties.apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.port", "587")
        }
        //val session = Session
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(toMail, toPass)
            }
        })
        try {
            val message: Message = MimeMessage(session)
            val str = email.split("@")
            val emaill = str[0].trim()
            message.apply {
                setFrom(InternetAddress(toMail))
                addRecipient(Message.RecipientType.TO, InternetAddress(email))
                subject = "VECTOR GYM - FORGOT PASSWORD"
                setText("Chào $emaill\nMật khẩu mới của bạn là: $newPass")
            }
            sendMail().execute(message)
            Toast.makeText(requireContext(), "Mật khẩu mới đã được gửi!", Toast.LENGTH_SHORT).show()
        }catch (e: MessagingException){
            e.printStackTrace()
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
        val user: String = binding.txtUserLogin.text.toString()
        val pass: String = binding.txtPassLogin.text.toString()
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

    fun createPassword(): String {
        val generator = Random()
        val value: Int = generator.nextInt((999999 - 100000) + 1) + 100000
        return "$value"
    }
    private class sendMail : AsyncTask<Message, String, String>() {
        override fun doInBackground(vararg params: Message?): String {
            try {
                Transport.send(params[0])
                return "Success!"
            }catch (e: MessagingException){
                e.printStackTrace()
                return "Fail!"
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public fun maHoa(original: String): String{
        try {
            val SECRET_KEY = "12345678"
            val skeySpec = SecretKeySpec(SECRET_KEY.toByteArray(),"DES")
            val cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE,skeySpec)
            val byteEncrypted: ByteArray? = cipher.doFinal(original.toByteArray())
            var encrypted: String = ""
            encrypted = Base64.getEncoder().encodeToString(byteEncrypted)
            return encrypted
        }catch (e: NoSuchAlgorithmException){
           return "${e.printStackTrace()}"
        }catch (e: NoSuchPaddingException){
           return "${e.printStackTrace()}"
        }catch (e: IllegalBlockSizeException){
           return "${e.printStackTrace()}"
        }catch (e: BadPaddingException){
           return "${e.printStackTrace()}"
        }catch (e: InvalidKeyException){
           return "${e.printStackTrace()}"
        }
    }
}