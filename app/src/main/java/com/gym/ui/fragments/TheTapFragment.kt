package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.gym.R
import com.gym.databinding.FragmentThetapBinding
import com.gym.model.KhachHangModel
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.DangKyViewPagerAdapter
import com.gym.ui.viewmodel.ViewModel

class TheTapFragment : FragmentNext() {
    private lateinit var binding: FragmentThetapBinding
    val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }
    var khachHang = KhachHangModel()
    var receiveKH = KhachHangModel()
    var khachHangs = ArrayList<KhachHangModel>()
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
        receiveKH()
        return binding.root
    }

    private fun receiveKH() {
        /*val args = this.arguments
        receiveKH = args!!.getParcelable("infoKH")!!*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = DangKyViewPagerAdapter(childFragmentManager,activity!!.lifecycle)
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
        }
    }
    fun initVewModel(){
        viewModel.getDSKhachHang()
        lifecycleScope.launchWhenCreated {
            viewModel.khachHangs.collect{ response ->
                if(response.isEmpty()){
                    return@collect
                }
                else{
                    khachHangs.addAll(response)
                    for(i in response.indices){
                        phones.add(response[i].sdt)
                    }
                }
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
                val s1: String = selectItem!!
                for(i in khachHangs.indices){
                    val s2 = khachHangs[i].sdt.trim()
                    if(s1.compareTo(s2) == 0){
                        khachHang = khachHangs[i]
                        binding.apply {
                            btnInfoKH.visibility = View.VISIBLE
                            if(khachHang.phai.equals("Nam")){
                                tvKhachHang.setText("Xin chào, ông ${khachHang.hoTen}")
                            }
                            else if(khachHang.phai.equals("Nữ")){
                                tvKhachHang.setText("Xin chào, bà ${khachHang.hoTen}")
                            }
                        }
                    }
                    else{
                        Toast.makeText(requireContext(), "Không tìm thấy thông tin!", Toast.LENGTH_SHORT).show()
                        txtSearchKH.setText("")
                    }
                }
            }

        }
    }
    fun dialogInfoKH(khachHang: KhachHangModel){
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
        dialog.setCancelable(true)
        dialog.show()
        //==============================================================
        var spLoai: AutoCompleteTextView = dialog.findViewById(R.id.spLoaiKH)
        var spPhai: AutoCompleteTextView = dialog.findViewById(R.id.spPhaiKH)
        var txtMaKH: EditText = dialog.findViewById(R.id.txtMaKH)
        var txtTenKH: EditText = dialog.findViewById(R.id.txtTenKH)
        var txtEmailKH: EditText = dialog.findViewById(R.id.txtEmailKH)
        var txtSdtKH: EditText = dialog.findViewById(R.id.txtPhoneKH)
        var txtDiaChi: EditText = dialog.findViewById(R.id.txtDiaChiKH)
        //-----------------------------------------------------
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyKH)
        val btnThem: Button = dialog.findViewById(R.id.btnThemKH)
        //-----------------------------------------------------
        spLoai.inputType = InputType.TYPE_NULL
        spPhai.inputType = InputType.TYPE_NULL
        txtMaKH.inputType = InputType.TYPE_NULL
        txtTenKH.inputType = InputType.TYPE_NULL
        txtEmailKH.inputType = InputType.TYPE_NULL
        txtSdtKH.inputType = InputType.TYPE_NULL
        txtDiaChi.inputType = InputType.TYPE_NULL
        btnHuy.visibility = View.GONE
        btnThem.visibility = View.GONE
        //==============================================================

    }
}