package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.gym.R
import com.gym.databinding.FragmentNhanvienBinding
import com.gym.model.LoaiKhModel
import com.gym.model.NhanVienModel
import com.gym.model.TaiKhoanModel
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.NhanVienAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class NhanVienFragment : FragmentNext(),NhanVienAdapter.OnItemClick {
    private lateinit var binding: FragmentNhanvienBinding
    var nhanVienAdapter = NhanVienAdapter(this@NhanVienFragment)
    var nhanViens = ArrayList<NhanVienModel>()
    var nhanVien = NhanVienModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //=====================
        //=====================
        binding = FragmentNhanvienBinding.inflate(layoutInflater)
        binding.imbBack.setOnClickListener {
            getFragment(requireView(),R.id.navNhanVienToHome)
        }
        refreshData()
        binding.pbLoad.visibility = View.VISIBLE
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            initViewModel()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imbAdd.setOnClickListener {
                dialogInsertNV()
            }
        }
    }
    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                initViewModel()
                refresh.isRefreshing = false
            }

        }
    }
    fun initViewModel() {
        //call api
        viewModel.getDSNhanVien()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.nhanViens.collect {response ->
                if (response.isEmpty()) {
                    binding.pbLoad.visibility = View.GONE
                    return@collect
                }
                else{
                    nhanVienAdapter.nhanViens = response
                    nhanViens.addAll(response)
                    initAdapter()
                    nhanVienAdapter.notifyDataSetChanged()
                    binding.pbLoad.visibility = View.GONE
                }
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvNhanVien.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = nhanVienAdapter
            }
        }
    }
    override fun itemClickEdit(nhanVien: NhanVienModel) {
        dialogEdit(nhanVien)
    }

    private fun dialogEdit(nhanVien: NhanVienModel) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_khachhang)

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
        //---------------------------------
        val tvSpLoaiKH: TextInputLayout = dialog.findViewById(R.id.tvSpLoaiKH)
        val tvSpPhaiKH: TextInputLayout = dialog.findViewById(R.id.tvSpPhaiKH)
        val spLoaiKH: AutoCompleteTextView = dialog.findViewById(R.id.spLoaiKH)
        val tvTitle: TextView = dialog.findViewById(R.id.tvTitleKH)
        val txtMaKH: EditText = dialog.findViewById(R.id.txtMaKH)
        val tvTenKH: TextInputLayout = dialog.findViewById(R.id.tvTenKH)
        val txtTenKH: EditText = dialog.findViewById(R.id.txtTenKH)
        val txtEmailKH: EditText = dialog.findViewById(R.id.txtEmailKH)
        val txtSdtKH: EditText = dialog.findViewById(R.id.txtPhoneKH)
        val spPhai: EditText = dialog.findViewById(R.id.spPhaiKH)
        val txtDiaChiKH: EditText = dialog.findViewById(R.id.txtDiaChiKH)
        val btnUpdate: Button = dialog.findViewById(R.id.btnThemKH)
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyKH)
        //---------------------------------
        //------------------Set name nv -----
        tvTitle.text = "Nhân Viên"
        tvSpLoaiKH.visibility = View.GONE
        //spLoaiKH.visibility = View.GONE
        txtMaKH.visibility = View.GONE
        tvTenKH.hint = "Tên nhân viên"
        //------------------------------------
        btnUpdate.text = "Cập nhật"
        txtTenKH.isEnabled = false
        spPhai.isEnabled = false
        txtEmailKH.isEnabled = false
        //---------------------------------
        txtTenKH.setText(nhanVien.hoTen)
        txtEmailKH.setText(nhanVien.email)
        txtSdtKH.apply {
            setText(nhanVien.sdt)
            requestFocus()
        }
        spPhai.setText(nhanVien.phai)
        txtDiaChiKH.setText(nhanVien.diaChi)
        //---------------------------------
        btnUpdate.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                viewModel.updateNhanVien(NhanVienModel(nhanVien.maNV,nhanVien.hoTen,nhanVien.email,txtSdtKH.text.toString(),nhanVien.phai,txtDiaChiKH.text.toString(),""))
                Toast.makeText(requireContext(), "Cập nhật nhân viên thành công!", Toast.LENGTH_SHORT).show()
            }
        }
        //---------------------------------
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun dialogInsertNV() {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_khachhang)

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
        //==============================================================
        val tvTitle: TextView = dialog.findViewById(R.id.tvTitleKH)
        val tvSpLoaiKH: TextInputLayout = dialog.findViewById(R.id.tvSpLoaiKH)
        val spLoaiKH: AutoCompleteTextView = dialog.findViewById(R.id.spLoaiKH)
        val spPhai: AutoCompleteTextView = dialog.findViewById(R.id.spPhaiKH)
        val txtMaKH: EditText = dialog.findViewById(R.id.txtMaKH)
        val tvTenKH: TextInputLayout = dialog.findViewById(R.id.tvTenKH)
        val txtTenKH: EditText = dialog.findViewById(R.id.txtTenKH)
        val txtEmailKH: EditText = dialog.findViewById(R.id.txtEmailKH)
        val txtSdtKH: EditText = dialog.findViewById(R.id.txtPhoneKH)
        val txtDiaChi: EditText = dialog.findViewById(R.id.txtDiaChiKH)
        //------------------Set name nv -----
        tvTitle.text = "Nhân Viên"
        tvSpLoaiKH.visibility = View.GONE
        //spLoaiKH.visibility = View.GONE
        txtMaKH.visibility = View.GONE
        tvTenKH.hint = "Tên nhân viên"
        //------------------------------------
        //-----------------------------------------------------
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyKH)
        val btnThem: Button = dialog.findViewById(R.id.btnThemKH)
        txtMaKH.visibility = View.GONE
        txtTenKH.requestFocus()
        //==============================================================
        val phais = listOf("Nam","Nữ")
        val arrAdapterPhai = ArrayAdapter(requireContext(),R.layout.dropdown_item,phais)
        var selectPhai: String = ""
        //-----------------------Phái-------------------------
        spPhai.setAdapter(arrAdapterPhai)
        spPhai.setOnItemClickListener { parent, view, position, id ->
            selectPhai = parent.getItemAtPosition(position).toString()
        }
        //----------------------------------------------------
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
        btnThem.setOnClickListener {
            val randomPass: String = createPassword()
            Toast.makeText(requireContext(), "maNV: ${randomString("nhân viên", getMaNVMax(nhanViens))} \ntenKH: ${replaceString(txtTenKH.text.toString())} \nphai: $selectPhai \nSDT: ${txtSdtKH.text} \nemail:${txtEmailKH.text} \nDiaChi: ${txtDiaChi.text}", Toast.LENGTH_SHORT).show()
            val insertNV = NhanVienModel(randomString("nhân viên", getMaNVMax(nhanViens)),replaceString(txtTenKH.text.toString()),txtEmailKH.text.toString(),txtSdtKH.text.toString(),selectPhai,txtDiaChi.text.toString(),"")
            val insertTK = TaiKhoanModel(createAccountRandom(txtEmailKH.text.toString()),randomPass,"Hoạt động","Q02",insertNV.maNV)
            viewModel.insertNhanVien(insertNV)
            lifecycleScope.launchWhenCreated {
                delay(2000L)
                viewModel.insertTaiKhoan(insertTK)
                //=====================
                val title = "VECTOR GYM - CẤP MỚI TÀI KHOẢN ĐĂNG NHẬP"
                val message = "Nhân viên: ${insertNV.hoTen}\nSố điện thoại:${insertNV.sdt}\nEmail:${insertNV.email} \nTÀI KHOẢN ĐĂNG NHẬP: ${insertTK.maTK}\nMẬT KHẨU: ${insertTK.matKhau}\n\n\nCẢM ƠN bạn đã đồng hành cùng VECTOR GYM!"
                val text = "Thêm nhân viên thành công!"
                sendMessageFromMail(insertNV.email,title,message,text)
                //=====================
                Log.e("TAG-CreateAccount", "user:${createAccountRandom(txtEmailKH.text.toString())}\npass:$randomPass\nTrạng thái: Hoạt động\nQuyền:Q02\nmaNV:${insertNV.maNV}")
                Toast.makeText(requireContext(), "created account successful!!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }
    fun dialogDelete(maNV: String){
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
        val title: TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)
        //-------------------------------
        nhanVien = getNhanVienByMaNV(maNV)
        //-------------------------------
        title.text = "Bạn thật sự muốn xóa nhân viên ${nhanVien.hoTen}?"
        btnYes.setOnClickListener {
            viewModel.deleteNhanVien(maNV)
            Toast.makeText(requireContext(), "Xóa nhân viên ${nhanVien.hoTen} thành công!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun getNhanVienByMaNV(maNV: String): NhanVienModel{
        var nhanVien = NhanVienModel()
        viewModel.getNhanVien(maNV)
        lifecycleScope.launchWhenCreated {
            viewModel.nhanVien.collect{
                if(it != null){
                    nhanVien = it
                }
                else
                    return@collect
            }
        }
        return nhanVien
    }
    fun getMaNVMax(nhanViens: ArrayList<NhanVienModel>): String {
        if (nhanViens.isNotEmpty()) {
            var max: Int = nhanViens[0].maNV.substring(2).toInt()
            var maMax = nhanViens[0].maNV
            for (i in nhanViens.indices) {
                if (max <= nhanViens[i].maNV.substring(2).toInt()) {
                    max = nhanViens[i].maNV.substring(2).toInt()
                    maMax = nhanViens[i].maNV
                }
            }
            return maMax
        }
        return "NV00"
    }
    override fun itemClickDelete(maNV: String) {
        dialogDelete(maNV)
    }

    override fun itemLongClick(nhanVienModel: NhanVienModel) {
        passDataKH(nhanVienModel)
    }

    private fun passDataKH(nhanVien: NhanVienModel) {
        val bundle = Bundle()
        bundle.putParcelable("infoKH",nhanVien)
        val fragment = TheTapFragment()
        fragment.arguments = bundle
        childFragmentManager.beginTransaction().replace(R.id.nav_fragment,fragment).commit()
        replaceFragment(R.id.nav_fragment,fragment)
    }
    private fun createAccountRandom(email: String): String{
        val msg = email.split("@")
        return msg[0]
    }
    private fun checkTaiKhoanByMaNV(maNV: String): Boolean{
        var status: Boolean = false
        viewModel.getDSTaiKhoan()
        lifecycleScope.launchWhenCreated {
            viewModel.taiKhoans.collect{
                if(it.isNotEmpty()){
                    for(i in it.indices){
                        if(maNV == it[i].maNV){
                            status = true
                            break
                        }
                    }
                }
                else{
                    return@collect
                }
            }
        }
        return status
    }
}