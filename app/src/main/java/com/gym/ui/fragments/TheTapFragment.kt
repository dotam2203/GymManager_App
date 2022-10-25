package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.*
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout
import com.gym.R
import com.gym.databinding.FragmentThetapBinding
import com.gym.model.KhachHangModel
import com.gym.model.LoaiKhModel
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.DangKyViewPagerAdapter


class TheTapFragment : FragmentNext() {
    private lateinit var binding: FragmentThetapBinding
    var khachHang = KhachHangModel()
    var khachHangs = ArrayList<KhachHangModel>()
    var loaiKHs = ArrayList<LoaiKhModel>()
    var phones = ArrayList<String>()
    val tabTitle = listOf("Đăng Ký","Đã Đăng Kí")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThetapBinding.inflate(layoutInflater)
        binding.btnInfoKH.visibility = View.GONE
        initVewModel()
        setControl()
        return binding.root
    }

    private fun passDataKH(khachHang: KhachHangModel) {
        val bundle = Bundle()
        bundle.putParcelable("dataKH", khachHang)
        childFragmentManager.setFragmentResult("passData", bundle)
        childFragmentManager.setFragmentResult("passData1", bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = DangKyViewPagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
            tab,position -> tab.text = tabTitle[position]
        }.attach()
        binding.apply {
            btnListKH.setOnClickListener {
                getFragment(view,R.id.navHomeToNguoiDung)
            }
            btnInfoKH.setOnClickListener {
                dialogInfoKH(khachHang)
            }
            btnInsertKH.setOnClickListener {
                dialogInsertKH()
            }
        }
    }

    private fun dialogInsertKH() {
        var status = true
        val dialog = Dialog(requireActivity())
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
        val tvSpLoai: TextInputLayout = dialog.findViewById(R.id.tvSpLoaiKH)
        val spLoai: AutoCompleteTextView = dialog.findViewById(R.id.spLoaiKH)
        val spPhai: AutoCompleteTextView = dialog.findViewById(R.id.spPhaiKH)
        val txtMaKH: EditText = dialog.findViewById(R.id.txtMaKH)
        val txtTenKH: EditText = dialog.findViewById(R.id.txtTenKH)
        val txtEmailKH: EditText = dialog.findViewById(R.id.txtEmailKH)
        val txtSdtKH: EditText = dialog.findViewById(R.id.txtPhoneKH)
        val txtDiaChi: EditText = dialog.findViewById(R.id.txtDiaChiKH)
        //-----------------------------------------------------
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyKH)
        val btnThem: Button = dialog.findViewById(R.id.btnThemKH)
        txtMaKH.visibility = View.GONE
        txtTenKH.requestFocus()
        //==============================================================
        val tenLoaiKHs = ArrayList<String>()
        val phais = listOf("Nam","Nữ")
        tenLoaiKHs.add("Bạc")
        val arrAdapterLoaiKH = ArrayAdapter(requireContext(),R.layout.dropdown_item,tenLoaiKHs)
        val arrAdapterPhai = ArrayAdapter(requireContext(),R.layout.dropdown_item,phais)
        var selectLoaiKH: String = ""
        var idLoaiKH: Int = 0
        var selectPhai: String = ""
        spLoai.setText("Bạc")
        //==========================Loại kh==============
        spLoai.setAdapter(arrAdapterLoaiKH)
        spLoai.setOnItemClickListener { parent, view, position, id ->
            selectLoaiKH = parent.getItemAtPosition(position).toString()
            for(i in loaiKHs.indices){
                if(selectLoaiKH == loaiKHs[i].tenLoaiKH){
                    idLoaiKH = 1
                }
            }
        }
        //------------------------------------------------
        //-----------------------Phái-------------------------
        spPhai.setAdapter(arrAdapterPhai)
        spPhai.setOnItemClickListener { parent, view, position, id ->
            selectPhai = parent.getItemAtPosition(position).toString()

        }
        //----------------------------------------------------
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }

        txtEmailKH.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!Patterns.EMAIL_ADDRESS.matcher(txtEmailKH.text.toString()).matches())
                    txtEmailKH.error = "Sai định dạng email!"
            }
        })
        btnThem.setOnClickListener {
            if(txtTenKH.text.toString() == "" || txtSdtKH.text.toString() == ""|| selectPhai == "" || !isValidEmail(txtEmailKH.text) ){
                if(txtTenKH.text.toString() == ""){
                    txtTenKH.apply {
                        error = "Tên không được để trống!"
                        requestFocus()
                    }
                }
                else if(selectPhai == ""){
                    spPhai.apply {
                        error = "Vui lòng chọn phái!"
                        requestFocus()
                    }
                }
                else if(txtSdtKH.text.toString() == "" || txtSdtKH.text.length <= 10){
                    txtSdtKH.apply {
                        error = "Số điện thoại gồm 10 số!"
                        requestFocus()
                    }
                }
                else if(!isValidEmail(txtEmailKH.text)){
                    txtEmailKH.apply {
                        error = "Sai định dạng email!"
                        requestFocus()
                    }
                }
            }
            else{
                viewModel.insertKhachHang(KhachHangModel(randomString("khách hàng", getMaKHMax(khachHangs)),replaceString(txtTenKH.text.toString()),txtEmailKH.text.toString(),txtSdtKH.text.toString(),selectPhai,txtDiaChi.text.toString(),"",1))
                dialogPopMessage("Thêm khách hàng thành công!",R.drawable.ic_ok)
                dialog.dismiss()
                txtEmailKH.setText("")
                txtDiaChi.setText("")
                txtSdtKH.setText("")
                txtTenKH.setText("")
            }
        }
    }
    private fun initVewModel(){
        viewModel.getDSKhachHang()
        lifecycleScope.launchWhenCreated {
            viewModel.khachHangs.collect{ response ->
                if(response.isNotEmpty()){
                    khachHangs.addAll(response)
                    for(i in response.indices){
                        phones.add(response[i].sdt)
                    }
                }
                else{
                    return@collect
                }
            }
        }
        viewModel.getDSLoaiKH()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiKHs.collect{
                if(it.isNotEmpty()){
                    loaiKHs.addAll(it)
                }
                else
                    return@collect
            }
        }
    }
    fun setControl(){
        binding.apply {
            var arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,phones)
            var selectItem: String? = ""
            txtSearchKH.setAdapter(arrayAdapter)
            txtSearchKH.setOnItemClickListener { parent, view, position, id ->
                selectItem = parent.getItemAtPosition(position).toString().trim()
            }
            btnSearch.setOnClickListener {
                //compare: s1 > s2 -> >0,
                //compare: s1 < s2 -> <0,
                //compare: s1 = s2 -> =0
                if(txtSearchKH.text.isEmpty()){
                    Toast.makeText(requireContext(), "Vui lòng điền thông tin tìm kiếm!!!", Toast.LENGTH_SHORT).show()
                }else{
                    val s1: String = selectItem!!
                    var check = false
                    for(i in khachHangs.indices){
                        val s2 = khachHangs[i].sdt.trim()
                        if(s1.compareTo(s2) == 0){
                            khachHang = khachHangs[i]
                            //pass data customer between two fragment
                            passDataKH(khachHang)
                            binding.apply {
                                btnInfoKH.visibility = View.VISIBLE
                                if(khachHang.phai.equals("Nam")){
                                    tvKhachHang.setText("Xin chào, anh ${khachHang.hoTen}")
                                }
                                else if(khachHang.phai.equals("Nữ")){
                                    tvKhachHang.setText("Xin chào, chị ${khachHang.hoTen}")
                                }
                                btnInsertKH.visibility = View.GONE
                            }
                            check = true
                            break
                        }

                    }
                    if(!check){
                        txtSearchKH.setText("")
                        Toast.makeText(requireContext(), "Không tìm thấy thông tin!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun dialogInfoKH(khachHang: KhachHangModel){
        val dialog = Dialog(requireActivity())
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
        dialog.setCancelable(true)
        dialog.show()
        //==============================================================
        val spLoai: AutoCompleteTextView = dialog.findViewById(R.id.spLoaiKH)
        val spPhai: AutoCompleteTextView = dialog.findViewById(R.id.spPhaiKH)
        val txtMaKH: EditText = dialog.findViewById(R.id.txtMaKH)
        val txtTenKH: EditText = dialog.findViewById(R.id.txtTenKH)
        val txtEmailKH: EditText = dialog.findViewById(R.id.txtEmailKH)
        val txtSdtKH: EditText = dialog.findViewById(R.id.txtPhoneKH)
        val txtDiaChi: EditText = dialog.findViewById(R.id.txtDiaChiKH)
        //-----------------------------------------------------
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyKH)
        val btnThem: Button = dialog.findViewById(R.id.btnThemKH)
        //-----------------------------------------------------
        spLoai.isEnabled = false
        spPhai.isEnabled = false
        txtMaKH.visibility = View.GONE
        txtTenKH.isEnabled = false
        txtEmailKH.isEnabled = false
        txtSdtKH.isEnabled = false
        txtDiaChi.isEnabled = false
        btnHuy.visibility = View.GONE
        btnThem.visibility = View.GONE
        //==============================================================
        viewModel.getLoaiKH(khachHang.idLoaiKH)
        lifecycleScope.launchWhenCreated {
            viewModel.loaiKH.collect{
                if(it != null){
                    spLoai.setText(it.tenLoaiKH)
                }
                else
                    return@collect
            }
        }
        spPhai.setText(khachHang.phai)
        txtTenKH.setText(khachHang.hoTen)
        txtEmailKH.setText(khachHang.email)
        txtSdtKH.setText(khachHang.sdt)
        txtDiaChi.setText(khachHang.diaChi)
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}