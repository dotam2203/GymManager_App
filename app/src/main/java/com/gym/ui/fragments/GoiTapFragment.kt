package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.R
import com.gym.databinding.FragmentGoitapBinding
import com.gym.model.GiaGtModel
import com.gym.model.GoiTapModel
import com.gym.model.LoaiGtModel
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount
import com.gym.ui.adapter.GoiTapAdapter
import kotlinx.coroutines.delay
import java.util.ResourceBundle.Control.getControl

class GoiTapFragment : FragmentNext(), GoiTapAdapter.OnItemClick {
    private lateinit var binding: FragmentGoitapBinding
    var goiTapAdapter = GoiTapAdapter(this@GoiTapFragment)
    var goiTaps = ArrayList<GoiTapModel>()
    var gias = ArrayList<GiaGtModel>()
    var loaiGTs = ArrayList<LoaiGtModel>()
    var tenLoaiGTs = ArrayList<String>()
    var loaiGT = LoaiGtModel()
    var gia = GiaGtModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoitapBinding.inflate(layoutInflater)
        initAdapter()
        refreshData()
        if(SingletonAccount.taiKhoan?.maQuyen == "Q02"){
            binding.imbAdd.visibility = View.GONE
        }
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            initViewModel()
            getTenLoaiGT()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imbAdd.setOnClickListener {
            dialogInsert()
        }
    }

    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                goiTapAdapter.notifyDataSetChanged()
                refresh.isRefreshing = false
                binding.pbLoad.visibility = View.GONE
            }
        }
    }

    private fun getLoaiGTByIDLoaiGT(idLoaiGT: Int) {
        viewModel.getLoaiGT(idLoaiGT)
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGT.collect { response ->
                if (response != null) {
                    loaiGT = response
                }
            }
        }
    }

    private fun getGiaByIDGia(idGia: Int?) {
        if (idGia != null) {
            viewModel.getGia(idGia)
            lifecycleScope.launchWhenCreated {
                viewModel.gia.collect { response ->
                    if (response != null)
                        gia = response
                }
            }
        } else return
    }

    private fun getTenLoaiGT() {
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect { response ->
                if (response.isEmpty()) {
                    binding.pbLoad.visibility = View.GONE
                    return@collect
                } else {
                    loaiGTs.addAll(response)
                    for (i in response.indices) {
                        tenLoaiGTs.add(loaiGTs[i].tenLoaiGT)
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        //call api
        viewModel.getDSGoiTap()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect { response ->
                if (response.isNotEmpty()) {
                    goiTapAdapter.goiTaps.clear()
                    goiTapAdapter.goiTaps.addAll(response)
                    goiTaps.addAll(response)
                    goiTapAdapter.notifyDataSetChanged()
                    binding.checkList.visibility = View.GONE
                    binding.pbLoad.visibility = View.GONE

                } else {
                    binding.checkList.visibility = View.VISIBLE
                    binding.pbLoad.visibility = View.GONE
                    goiTapAdapter.goiTaps.clear()
                    goiTapAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvGoiTap.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = goiTapAdapter
            }
        }
    }

    private fun getDataGT(success: String, fail: String) {
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect { response ->
                goiTapAdapter.goiTaps.clear()
                if (response.isNotEmpty()) {
                    goiTapAdapter.goiTaps.addAll(response)
                    goiTapAdapter.notifyDataSetChanged()
                    Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, fail, Toast.LENGTH_SHORT).show()
                    return@collect
                }
            }
        }
    }

    private fun getMaGTMax(goiTaps: ArrayList<GoiTapModel>): String {
        if (goiTaps.isNotEmpty()) {
            var max: Int = goiTaps[0].maGT.substring(2).toInt()
            var maMax = goiTaps[0].maGT
            for (i in goiTaps.indices) {
                if (max <= goiTaps[i].maGT.substring(2).toInt()) {
                    max = goiTaps[i].maGT.substring(2).toInt()
                    maMax = goiTaps[i].maGT
                }
            }
            return maMax
        }
        return "GT00"
    }

    private fun dialogInsert() {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_goitap)

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
        val txtTenGT: EditText = dialog.findViewById(R.id.txtTenGT)
        val txtTrangThai: EditText = dialog.findViewById(R.id.txtTTGoiTap)
        val txtGiaGT: EditText = dialog.findViewById(R.id.txtGiaGT)
        val txtNgayAD: EditText = dialog.findViewById(R.id.txtNgayAD)
        val txtMoTa: EditText = dialog.findViewById(R.id.txtMoTaGT)
        val btnThem: Button = dialog.findViewById(R.id.btnThemGT)
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyGT)
        val imbCalendar: ImageButton = dialog.findViewById(R.id.imbCalendar)
        imbCalendar.visibility = View.GONE
        //----------------------------spinner--------------------------
        getControl(imbCalendar, txtNgayAD)
        txtTrangThai.apply {
            setText("Hoạt động")
            isEnabled = false
        }
        Log.e("TAG", "GoiTaps size: ${goiTaps.size}")
        val randomMaGT: String = randomString("gói tập", getMaGTMax(goiTaps))
        val spinner: AutoCompleteTextView = dialog.findViewById(R.id.spLoaiGT)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, tenLoaiGTs)
        var selectItem: String? = ""
        var idLoaiGT: Int = 0
        val maNV: String = SingletonAccount.taiKhoan?.maNV.toString()
        spinner.setAdapter(arrayAdapter)
        spinner.setOnItemClickListener { parent, view, position, id ->
            selectItem = parent.getItemAtPosition(position).toString()
            for (i in loaiGTs.indices) {
                if (selectItem == loaiGTs[i].tenLoaiGT) {
                    idLoaiGT = loaiGTs[i].idLoaiGT
                    break
                }
            }
        }
        btnThem.setOnClickListener {
            val giaGt = GiaGtModel()
            giaGt.ngayApDung = getFormatDateToApi(txtNgayAD.text.toString())
            giaGt.gia = txtGiaGT.text.toString()
            giaGt.maGT = randomMaGT
            giaGt.maNV = maNV
            for (i in goiTaps.indices) {
                val tenGT = replaceString(txtTenGT.text.toString()).trim()
                if ((tenGT == goiTaps[i].tenGT.trim()) && (idLoaiGT == goiTaps[i].idLoaiGT)) {
                    Toast.makeText(activity, "Loại dịch vụ này đã tồn tại!", Toast.LENGTH_SHORT).show()
                    spinner.setText("")
                    selectItem = ""
                    spinner.requestFocus()
                    break
                }
            }
            if(selectItem != ""){
                try {
                    viewModel.insertGoiTapGia(GoiTapModel(randomMaGT, txtTenGT.text.toString().replace(",",""), txtMoTa.text.toString(), txtTrangThai.text.toString(), idLoaiGT), giaGt)
                    dialogPopMessage("Thêm gói tập thành công!",R.drawable.ic_ok)
                }catch (ex:Exception){
                    dialogPopMessage("Thêm gói tập thất bại!",R.drawable.ic_fail)
                }

                spinner.setText("")
                txtTenGT.setText("")
                txtGiaGT.setText("")
                txtNgayAD.setText("")
                txtMoTa.setText("")
                spinner.requestFocus()
            }
        }
        //-------------------------------------------------------------

        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun dialogEdit(goiTap: GoiTapModel) {
        var status : Boolean = false
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_goitap)

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
        val imbCalendar: ImageButton = dialog.findViewById(R.id.imbCalendar)
        val tvTitle: TextView = dialog.findViewById(R.id.tvTitleGoiTap)
        val txtTenGT: EditText = dialog.findViewById(R.id.txtTenGT)
        val txtTrangThai: EditText = dialog.findViewById(R.id.txtTTGoiTap)
        val txtGiaGT: EditText = dialog.findViewById(R.id.txtGiaGT)
        val txtNgayAD: EditText = dialog.findViewById(R.id.txtNgayAD)
        val txtMoTa: EditText = dialog.findViewById(R.id.txtMoTaGT)
        val btnThem: Button = dialog.findViewById(R.id.btnThemGT)
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyGT)
        //----------------------------spinner--------------------------
        txtTrangThai.apply {
            isEnabled = true
        }
        imbCalendar.visibility = View.GONE
        var spinner: AutoCompleteTextView = dialog.findViewById(R.id.spLoaiGT)
        /*val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, tenLoaiGTs)
        var selectItem: String? = ""*/
        var idLoaiGT: Int = 0
        val maNV: String = SingletonAccount.taiKhoan?.maNV.toString()
        //===========================
        spinner.setText(goiTap.tenLoaiGT)
        spinner.isEnabled = false

        txtTenGT.setText(goiTap.tenGT)
        txtTrangThai.setText(goiTap.trangThai)
        txtMoTa.setText(goiTap.moTa)
        //txtGiaGT.setText(formatMoney(goiTap.giaGoiTaps!![0].gia))
        txtGiaGT.setText(goiTap.giaGoiTaps!![0].gia)
        txtNgayAD.setText(getFormatDateFromAPI(goiTap.giaGoiTaps!![0].ngayApDung))
        getControl(imbCalendar,txtNgayAD)
        btnThem.text = "Cập nhật"
        btnHuy.text = "Thoát"
        //============================
        if(SingletonAccount.taiKhoan!!.maQuyen == "Q02" || !goiTap.ctTheTaps.isNullOrEmpty()){
            txtGiaGT.setText("${formatMoney(goiTap.giaGoiTaps!![0].gia)} đ")
            dialog.setCancelable(true)
            dialog.show()
            tvTitle.text = "Chi Tiết Gói Tập"
            btnThem.visibility = View.GONE
            btnHuy.visibility = View.GONE
            txtTenGT.isEnabled = false
            txtTrangThai.isEnabled = false
            txtGiaGT.isEnabled = false
            txtMoTa.isEnabled = false
        }
        //============================
    else{
        btnThem.setOnClickListener {
            for (i in goiTaps.indices) {
                val tenGT = replaceString(txtTenGT.text.toString()).trim()
                if (tenGT == goiTaps[i].tenGT.trim() && tenGT != goiTap.tenGT) {
                    status = true
                    Toast.makeText(activity, "Dịch vụ này đã tồn tại!", Toast.LENGTH_SHORT).show()
                    txtTenGT.setText(goiTap.tenGT)
                    txtTenGT.requestFocus()
                    break
                }
            }
            if(!status){
                try {
                    val giaGtModel = GiaGtModel(goiTap.giaGoiTaps!![0].idGia, getFormatDateToApi(txtNgayAD.text.toString()), txtGiaGT.text.toString().replace(",",""), goiTap.maGT, maNV)
                    viewModel.updateGia(giaGtModel)
                    viewModel.updateGoiTap(GoiTapModel(goiTap.maGT, replaceString(txtTenGT.text.toString()), txtMoTa.text.toString(), txtTrangThai.text.toString(), goiTap.idLoaiGT))
                    dialogPopMessage("Cập nhật thông tin thành công!", R.drawable.ic_ok)
                    goiTapAdapter.notifyDataSetChanged()
                    txtTenGT.requestFocus()
                    //dialog.dismiss()
                } catch (ex: Exception){
                    dialogPopMessage("Cập nhật thông tin thất bại!", R.drawable.ic_fail)
                    txtTenGT.requestFocus()
                }
            }
        }
        //-------------------------------------------------------------

        btnHuy.setOnClickListener {
            dialog.dismiss()
        }}
    }
    private fun getControl(imbCalendar: ImageButton, txtNgayAD: EditText) {
        txtNgayAD.setText(getCurrentDate())
        imbCalendar.setOnClickListener {
            val datePickerFragment = DatePickerFagment()
            val support = requireActivity().supportFragmentManager
            //receive date
            support.setFragmentResultListener("REQUEST_KEY", viewLifecycleOwner) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("PASS_DATE")
                    txtNgayAD.setText(date)
                }
            }
            //show dialog
            datePickerFragment.show(support, "DatePickerFragment")
        }
    }

    override fun itemClickEdit(goiTapModel: GoiTapModel) {
        dialogEdit(goiTapModel)
    }

    override fun itemClickDelete(goiTapModel: GoiTapModel){
        dialogYN(goiTapModel)
        return
    }
    private fun dialogYN(goiTapModel: GoiTapModel) {
        val dialog = Dialog(activity!!)
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
        val tvMess: TextView = dialog.findViewById(R.id.tvMessage)
        tvMess.text = "Bạn chắc chắn muốn xóa ${goiTapModel.tenGT}?"
        btnYes.setOnClickListener {
            try {
                viewModel.deleteGoiTap(goiTapModel.maGT)

                lifecycleScope.launchWhenCreated {
                    delay(500L)
                    viewModel.deleteGia(goiTapModel.giaGoiTaps!![0].idGia)
                    dialogPopMessage("Xoá dịch vụ thành công!", R.drawable.ic_ok)
                }
                dialogPopMessage("Loading...", R.drawable.ic_load11)
                goiTapAdapter.notifyDataSetChanged()
                dialog.dismiss()
            } catch (ex: Exception) {
                dialogPopMessage("Xóa dịch vụ thất bại", R.drawable.ic_fail)
            }
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }
}

