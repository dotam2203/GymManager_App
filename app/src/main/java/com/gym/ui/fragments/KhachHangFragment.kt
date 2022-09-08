package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentKhachhangBinding
import com.gym.model.KhachHangModel
import com.gym.model.LoaiKhModel
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.KhachHangAdapter
import kotlinx.coroutines.delay

class KhachHangFragment : FragmentNext(), KhachHangAdapter.OnItemClick {
    private lateinit var binding: FragmentKhachhangBinding
    var khachHangAdapter = KhachHangAdapter(this@KhachHangFragment)
    var khachHangs = ArrayList<KhachHangModel>()
    var khachHang = KhachHangModel()
    var loaiKHs = ArrayList<LoaiKhModel>()
    var tenLoaiKHs = ArrayList<String>()
    var loaiKH = LoaiKhModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKhachhangBinding.inflate(layoutInflater)
        initAdapter()
        refreshData()
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            initViewModel()
        }
        getTenLoaiKH()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*binding.apply {
            imbAdd.setOnClickListener {
                dialog()
            }
        }*/
    }
    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                khachHangAdapter.notifyDataSetChanged()
                refresh.isRefreshing = false
                binding.pbLoad.visibility = View.GONE
            }
        }
    }
    private fun initViewModel() {
        //call api
        viewModel.getDSKhachHang()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.khachHangs.collect {response ->
                if (response.isNotEmpty()) {
                    khachHangAdapter.khachHangs.clear()
                    khachHangAdapter.khachHangs.addAll(response)
                    khachHangs.addAll(response)
                    khachHangAdapter.notifyDataSetChanged()
                    binding.checkList.visibility = View.GONE
                    binding.pbLoad.visibility = View.GONE
                }
                else {
                    khachHangAdapter.khachHangs.clear()
                    khachHangAdapter.notifyDataSetChanged()
                    binding.checkList.visibility = View.VISIBLE
                    binding.pbLoad.visibility = View.GONE
                }
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvKhachHang.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = khachHangAdapter
            }
        }
    }

    fun getTenLoaiKH(){
        viewModel.getDSLoaiKH()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiKHs.collect{ response ->
                if(response.isEmpty()){
                    return@collect
                }
                else{
                    loaiKHs.addAll(response)
                    for (i in response.indices){
                        tenLoaiKHs.add(loaiKHs[i].tenLoaiKH)
                    }
                }
            }
        }
    }
    fun getLoaiKHByID(idLoaiKH: Int){
        viewModel.getLoaiKH(idLoaiKH)
        lifecycleScope.launchWhenCreated {
            viewModel.loaiKH.collect{ response ->
                if(response == null){
                    return@collect
                }
                else{
                    loaiKH = response
                }
            }
        }
    }

    override fun itemClickEdit(khachHang: KhachHangModel) {
        dialogEdit(khachHang)
    }
    private fun dialogEdit(khachHang: KhachHangModel) {
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
        val spLoaiKH: AutoCompleteTextView = dialog.findViewById(R.id.spLoaiKH)
        val txtMaKH: EditText = dialog.findViewById(R.id.txtMaKH)
        val txtTenKH: EditText = dialog.findViewById(R.id.txtTenKH)
        val txtEmailKH: EditText = dialog.findViewById(R.id.txtEmailKH)
        val txtSdtKH: EditText = dialog.findViewById(R.id.txtPhoneKH)
        val txtPhaiKH: EditText = dialog.findViewById(R.id.spPhaiKH)
        val txtDiaChiKH: EditText = dialog.findViewById(R.id.txtDiaChiKH)
        val btnUpdate: Button = dialog.findViewById(R.id.btnThemKH)
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyKH)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,tenLoaiKHs)
        var selectItem: String? = ""
        var idLoaiKH: Int = 0
        //---------------------------------
        txtMaKH.visibility = View.GONE
        btnUpdate.setText("Cập nhật")
        getLoaiKHByID(khachHang.idLoaiKH)
        spLoaiKH.setText(loaiKH.tenLoaiKH)
        txtTenKH.setText(khachHang.hoTen)
        txtEmailKH.setText(khachHang.email)
        txtSdtKH.setText(khachHang.sdt)
        txtPhaiKH.setText(khachHang.phai)
        txtDiaChiKH.setText(khachHang.diaChi)
        txtTenKH.isEnabled = false
        txtPhaiKH.isEnabled = false
        //---------------------------------
        spLoaiKH.setText(tenLoaiKHs[0])
        spLoaiKH.setAdapter(arrayAdapter)
        spLoaiKH.setOnItemClickListener { parent, view, position, id ->
            selectItem = parent.getItemAtPosition(position).toString()
        }
        for(i in loaiKHs.indices){
            if(selectItem == loaiKHs[i].tenLoaiKH)
                idLoaiKH = loaiKHs[i].idLoaiKH
        }
        btnUpdate.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                viewModel.updateKhachHang(KhachHangModel(khachHang.maKH,replaceString(txtTenKH.text.toString()),txtEmailKH.text.toString(),txtSdtKH.text.toString(),txtPhaiKH.text.toString(),txtDiaChiKH.text.toString(),selectItem.toString(),idLoaiKH))
            }
        }
        //---------------------------------
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun dialogDelete(maKH: String){
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
        khachHang = getKhachHangByMaKH(maKH)
        //-------------------------------
        title.text = "Bạn thật sự muốn xóa khách hàng ${khachHang.hoTen}?"
        btnYes.setOnClickListener {
            viewModel.deleteKhachHang(maKH)
            Toast.makeText(requireContext(), "Xóa khách hàng ${khachHang.hoTen} thành công!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun getKhachHangByMaKH(maKH: String): KhachHangModel {
        var khachHang = KhachHangModel()
        viewModel.getKhachHang(maKH)
        lifecycleScope.launchWhenCreated {
            viewModel.khachHang.collect{
                if(it != null){
                    khachHang = it
                }
                else
                    return@collect
            }
        }
        return khachHang
    }
    override fun itemClickDelete(maKH: String) {
        dialogDelete(maKH)
    }

    override fun itemLongClick(khachHangModel: KhachHangModel) {
        passDataKH(khachHangModel)
    }

    private fun passDataKH(khachHang: KhachHangModel) {
        val bundle = Bundle()
        bundle.putParcelable("infoKH",khachHang)
        val fragment = TheTapFragment()
        fragment.arguments = bundle
        childFragmentManager.beginTransaction().replace(R.id.nav_fragment,fragment).commit()
        replaceFragment(R.id.nav_fragment,fragment)
        //getFragment(view!!, R.id.navKhachHangToDangKy)
    }
}