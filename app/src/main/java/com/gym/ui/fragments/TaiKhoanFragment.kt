package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.gym.R
import com.gym.databinding.FragmentTaikhoanBinding
import com.gym.model.NhanVienModel
import com.gym.model.TaiKhoanModel
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount
import com.gym.ui.viewmodel.ViewModel

class TaiKhoanFragment : FragmentNext() {
    private lateinit var binding: FragmentTaikhoanBinding
    val viewModel: ViewModel by lazy{
        ViewModelProvider(this).get(ViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaikhoanBinding.inflate(layoutInflater)
        showDataUser()
        binding.imbSaveAcc.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imbLogout.setOnClickListener {
                //dialogLogout()
            }
            imbEditAcc.setOnClickListener {
                txtPassAcc.isEnabled = true
                txtPassAcc.requestFocus()
                txtNameAcc.isEnabled = true
                txtPhoneAcc.isEnabled = true
                txtAdressAcc.isEnabled = true

                imbEditAcc.visibility = View.GONE
                imbSaveAcc.visibility = View.VISIBLE
            }
            imbSaveAcc.setOnClickListener {
                //update data
                val maQuyen: String = SingletonAccount.taiKhoan?.maQuyen.toString()
                val maNV: String = SingletonAccount.taiKhoan?.maNV.toString()
                val user: String = SingletonAccount.taiKhoan?.maTK.toString()
                viewModel.updateTaiKhoan(TaiKhoanModel(user,txtPassAcc.text.toString(),"Hoạt động",maQuyen,maNV))
                viewModel.updateNhanVien(NhanVienModel(maNV,txtNameAcc.text.toString(),txtEmailAcc.text.toString(),txtPhoneAcc.text.toString(),"Nữ",txtAdressAcc.text.toString()))
                viewModel.nhanVien.observe(viewLifecycleOwner){
                    if(it != null){
                        Toast.makeText(requireContext(), "update staff successful!", Toast.LENGTH_SHORT).show()
                    }
                    else
                        Toast.makeText(requireContext(), "update staff failed!", Toast.LENGTH_SHORT).show()
                }
                viewModel.taiKhoan.observe(viewLifecycleOwner){
                    if(it != null){
                        Toast.makeText(requireContext(), "update acc successful!", Toast.LENGTH_SHORT).show()
                        showDataUser()
                    }
                    else
                        Toast.makeText(requireContext(), "update acc failed!", Toast.LENGTH_SHORT).show()
                }
                imbEditAcc.visibility = View.VISIBLE
                imbSaveAcc.visibility = View.GONE
            }
        }
    }

    private fun dialogLogout() {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_message)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes : WindowManager.LayoutParams = window!!.attributes
        windowAtrributes.gravity = Gravity.CENTER
        window.attributes = windowAtrributes
        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        dialog.setCancelable(false)
        dialog.show()

        var btnYes: Button = dialog.findViewById(R.id.btnYes)
        var btnNo: Button = dialog.findViewById(R.id.btnNo)
        btnYes.setOnClickListener {
            //getFragment(requireView(),R.id.navTaikhoanToLogin)
            //replaceFragment(R.id.fragmentLogin, LoginFragment())
            activity!!.finish()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun showDataUser(){
        binding.apply {
            viewModel.getQuyen(SingletonAccount.taiKhoan!!.maQuyen.toString())
            viewModel.quyen.observe(viewLifecycleOwner){ response ->
                tvQuyen.text = response!!.tenQuyen
            }
            viewModel.getNhanVien(SingletonAccount.taiKhoan!!.maNV.toString())
            viewModel.nhanVien.observe(viewLifecycleOwner){ response ->
                txtUserAcc.apply {
                    setText(SingletonAccount.taiKhoan!!.maTK)
                    isEnabled = false
                }
                txtPassAcc.apply {
                    setText(SingletonAccount.taiKhoan!!.matKhau)
                    isEnabled = false
                }
                txtNameAcc.apply {
                    setText(response!!.hoTen)
                    isEnabled = false
                }
                txtEmailAcc.apply {
                    setText(response!!.email)
                    isEnabled = false
                }
                txtPhoneAcc.apply {
                    setText(response!!.sdt)
                    isEnabled = false
                }
                txtAdressAcc.apply {
                    setText(response!!.diaChi)
                    isEnabled = false
                }
            }

        }

    }

}