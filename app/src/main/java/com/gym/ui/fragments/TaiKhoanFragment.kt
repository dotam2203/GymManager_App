package com.gym.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide.init
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.gym.R
import com.gym.databinding.FragmentTaikhoanBinding
import com.gym.model.NhanVienModel
import com.gym.model.TaiKhoanModel
import com.gym.ui.FragmentNext
import com.gym.ui.SharedPreferencesLogin
import com.gym.ui.SingletonAccount
import kotlinx.coroutines.delay
import java.io.File

class TaiKhoanFragment : FragmentNext() {
    private lateinit var binding: FragmentTaikhoanBinding
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private var fileName = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //callBack(R.id.navTaikhoanToHome)
        binding = FragmentTaikhoanBinding.inflate(layoutInflater)
        init()
        refreshData()
        showDataUser()
        //=====================
        binding.imbSaveAcc.visibility = View.GONE
        //==========upload image==========
        val getImage = registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
            if (it != null) {
                imageUri = it
            }
            binding.imvAvatar.setImageURI(imageUri)
        })
        binding.imvUpload.setOnClickListener {
            getImage.launch("image/*")
            uploadImage()
        }
        //==============================
        return binding.root
    }
    private fun retrieveImage(fileName: String){
        val storageRef = FirebaseStorage.getInstance().reference.child("image/$fileName.jpg")
        val localFile = File.createTempFile("image","jpg")
        storageRef.getFile(localFile).addOnSuccessListener{
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.imvAvatar.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(requireActivity(), "Retrieve image failed: $it!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun uploadImage() {
        val msg = getRandomMaHD()
        fileName = "avt${msg.substring(2)}"
        storageReference = FirebaseStorage.getInstance().getReference("image/${fileName}")
        dialogPopMessage("Uploading file...", R.drawable.ic_load11)
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            storageReference.putFile(imageUri)
                .addOnSuccessListener {
                    //binding.imvAvatar.setImageURI(imageUri)
                    lifecycleScope.launchWhenCreated {
                        delay(3000L)
                        retrieveImage(fileName)
                    }
                    Toast.makeText(requireContext(), "Cập nhật ảnh đại diện thành công!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener(OnFailureListener {
                    Toast.makeText(requireContext(), "Cập nhật ảnh đại diện thất bại!: $it!", Toast.LENGTH_SHORT).show()
                })
        }
    }
    private fun init() {
        imageUri = Uri.EMPTY
        sharedPreferencesLogin = SharedPreferencesLogin(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imbLogout.setOnClickListener {
                dialogLogout()
            }
            imbEditAcc.setOnClickListener {
                txtUserAcc.setTextColor(R.color.gray_100)
                txtEmailAcc.setTextColor(R.color.gray_100)
                txtPassAcc.isEnabled = true
                txtPassAcc.requestFocus()
                txtNameAcc.isEnabled = true
                txtPhoneAcc.isEnabled = true
                txtAdressAcc.isEnabled = true

                imbEditAcc.visibility = View.GONE
                imbSaveAcc.visibility = View.VISIBLE

            }
            imbSaveAcc.setOnClickListener {
                txtUserAcc.setTextColor(R.color.black)
                txtEmailAcc.setTextColor(R.color.black)
                //update data
                val maQuyen: String = SingletonAccount.taiKhoan?.maQuyen.toString()
                val maNV: String = SingletonAccount.taiKhoan?.maNV.toString()
                val user: String = SingletonAccount.taiKhoan?.maTK.toString()
                lifecycleScope.launchWhenCreated {
                    viewModel.updateNhanVien(NhanVienModel(maNV, txtNameAcc.text.toString(), txtEmailAcc.text.toString(), txtPhoneAcc.text.toString(), "Nữ", txtAdressAcc.text.toString()))
                }
                lifecycleScope.launchWhenCreated {
                    viewModel.updateTaiKhoan(TaiKhoanModel(user, maHoaPassApi(txtPassAcc.text.toString()), "Hoạt động", maQuyen, maNV))
                    delay(1000L)
                    dialogPopMessage("Cập nhật thông tin thành công!",R.drawable.ic_ok)
                }
                imbEditAcc.visibility = View.VISIBLE
                imbSaveAcc.visibility = View.GONE
            }
        }
    }

    private fun dialogLogout() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_message)

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

        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)
        btnYes.setOnClickListener {
            dialog.dismiss()
            dialogPopMessage("Loading...", R.drawable.ic_load11)
            //binding.pbLoad.visibility = View.VISIBLE
            sharedPreferencesLogin.removeData()
            lifecycleScope.launchWhenCreated {
                delay(3000L)
                getFragment(requireView(), R.id.navHomeToLogin)
            }
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun showDataUser() {
        binding.apply {
            viewModel.getQuyen(SingletonAccount.taiKhoan?.maQuyen.toString())
            lifecycleScope.launchWhenCreated {
                viewModel.quyen.collect { response ->
                    tvQuyen.text = response?.tenQuyen
                }
            }
            viewModel.getNhanVien(SingletonAccount.taiKhoan?.maNV.toString())
            lifecycleScope.launchWhenCreated {
                viewModel.nhanVien.collect { response ->
                    txtUserAcc.apply {
                        setText(SingletonAccount.taiKhoan?.maTK)
                        isEnabled = false
                    }
                    txtPassAcc.apply {
                        setText(sharedPreferencesLogin.getPass())
                        isEnabled = false
                    }
                    txtNameAcc.apply {
                        setText(response?.hoTen)
                        isEnabled = false
                    }
                    txtEmailAcc.apply {
                        setText(response?.email)
                        isEnabled = false
                    }
                    txtPhoneAcc.apply {
                        setText(response?.sdt)
                        isEnabled = false
                    }
                    txtAdressAcc.apply {
                        setText(response?.diaChi)
                        isEnabled = false
                    }
                }
            }
        }
    }

    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                showDataUser()
                refresh.isRefreshing = false
            }

        }
    }
}