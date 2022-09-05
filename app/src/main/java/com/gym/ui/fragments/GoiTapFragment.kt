package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.ViewModelProvider
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
import com.gym.ui.viewmodel.ViewModel
import kotlinx.coroutines.delay
import java.util.*

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
        if(SingletonAccount.taiKhoan?.maQuyen == "Q02"){
            binding.imbAdd.visibility = View.GONE
        }
        binding.pbLoad.visibility = View.VISIBLE
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            initViewModel()
            getTenLoaiGT()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshData()
        binding.imbAdd.setOnClickListener {
            dialogInsert()
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

    fun getLoaiGTByIDLoaiGT(idLoaiGT: Int) {
        viewModel.getLoaiGT(idLoaiGT)
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGT.collect { response ->
                if (response != null) {
                    loaiGT = response
                }
            }
        }
    }

    fun getGiaByIDGia(idGia: Int?) {
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

    fun getTenLoaiGT() {
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

    fun checkIDGiaByMaGT(goiTap: GoiTapModel, gias: ArrayList<GiaGtModel>): Boolean {
        for (i in gias.indices) {
            if (goiTap.maGT == gias[i].maGT) {
                getGiaByIDGia(gias[i].idGia)
                return true
            }
        }
        return false
    }

    fun initViewModel() {
        //call api
        viewModel.getDSGoiTap()
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect { response ->
                if (response.isNotEmpty()) {
                    initAdapter()
                    goiTapAdapter.goiTaps = response
                    goiTaps.addAll(response)
                    goiTapAdapter.notifyDataSetChanged()
                    binding.pbLoad.visibility = View.GONE

                } else {
                    binding.pbLoad.visibility = View.GONE
                    Toast.makeText(activity, "List null!", Toast.LENGTH_SHORT).show()
                    return@collect
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.goiTap.collect { response ->
                response ?: return@collect
                goiTaps.add(response)
                goiTapAdapter.goiTaps = goiTaps
                goiTapAdapter.notifyDataSetChanged()
                binding.pbLoad.visibility = View.GONE
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
                if (response.isNotEmpty()) {
                    goiTapAdapter.goiTaps = response
                    goiTapAdapter.notifyDataSetChanged()
                    Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, fail, Toast.LENGTH_SHORT).show()
                    return@collect
                }
            }
        }
    }

    private fun getDataGia(success: String, fail: String) {
        //Livedata observer
        lifecycleScope.launchWhenCreated {
            viewModel.gias.collect { response ->
                if (response.isNotEmpty()) {
                    gias.addAll(response)
                    Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, fail, Toast.LENGTH_SHORT).show()
                    return@collect
                }
            }
        }
    }

    fun getMaGTMax(goiTaps: ArrayList<GoiTapModel>): String {
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

    fun dialogInsert() {
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
        val txtMaGT: EditText = dialog.findViewById(R.id.txtMaGT)
        val txtTenGT: EditText = dialog.findViewById(R.id.txtTenGT)
        val txtTrangThai: EditText = dialog.findViewById(R.id.txtTTGoiTap)
        val txtGiaGT: EditText = dialog.findViewById(R.id.txtGiaGT)
        val txtNgayAD: EditText = dialog.findViewById(R.id.txtNgayAD)
        val txtMoTa: EditText = dialog.findViewById(R.id.txtMoTaGT)
        val btnThem: Button = dialog.findViewById(R.id.btnThemGT)
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyGT)
        val imbCalendar: ImageButton = dialog.findViewById(R.id.imbCalendar)
        //----------------------------spinner--------------------------
        txtMaGT.visibility = View.GONE
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
        //spinner.setText(tenLoaiGTs[0])
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
            lifecycleScope.launchWhenCreated {
                //txtMaGT.text.toString()
                val giaGt = GiaGtModel()
                giaGt.ngayApDung = getFormatDateApi(txtNgayAD.text.toString())
                giaGt.gia = txtGiaGT.text.toString()
                giaGt.maGT = randomMaGT
                giaGt.maNV = maNV
                for (i in goiTaps.indices) {
                    val tenGT = replaceString(txtTenGT.text.toString()).trim()
                    if ((tenGT == goiTaps[i].tenGT.trim()) && (idLoaiGT == goiTaps[i].idLoaiGT)) {
                        Toast.makeText(activity, "Loại dịch vụ này đã tồn tại!", Toast.LENGTH_SHORT).show()
                        Log.e("ERROR", "Loại dịch vụ này đã tồn tại!", )
                        spinner.setText("")
                        selectItem = ""
                        spinner.requestFocus()
                        break
                    }
                }
                if(selectItem != ""){
                    //GiaGtModel(0,txtNgayAD.text.toString(),txtGiaGT.text.toString(),randomMaGT,maNV)
                    viewModel.insertGoiTapGia(GoiTapModel(randomMaGT, replaceString(txtTenGT.text.toString()), txtMoTa.text.toString(), txtTrangThai.text.toString(), idLoaiGT), giaGt)
                    Toast.makeText(activity, "Thêm thành công!", Toast.LENGTH_SHORT).show()
                    Log.e("ERROR", "Thêm thành công!", )
                    spinner.setText("")
                    txtMaGT.setText("")
                    txtTenGT.setText("")
                    txtGiaGT.setText("")
                    txtNgayAD.setText("")
                    txtMoTa.setText("")
                    spinner.requestFocus()
                }
            }
        }
        //-------------------------------------------------------------

        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun dialogEdit(goiTap: GoiTapModel) {
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
        val txtMaGT: EditText = dialog.findViewById(R.id.txtMaGT)
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
        var spinner: AutoCompleteTextView = dialog.findViewById(R.id.spLoaiGT)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, tenLoaiGTs)
        var selectItem: String? = ""
        var idLoaiGT: Int = 0
        val maNV: String = SingletonAccount.taiKhoan?.maNV.toString()
        //===========================
        getLoaiGTByIDLoaiGT(goiTap.idLoaiGT)
        spinner.setText(loaiGT.tenLoaiGT)
        txtMaGT.visibility = View.GONE
        txtTenGT.setText(goiTap.tenGT)
        txtTrangThai.setText(goiTap.trangThai)
        if (checkIDGiaByMaGT(goiTap, gias)) {
            txtGiaGT.setText(gia.gia)
            txtNgayAD.setText(gia.ngayApDung)
        }
        txtMaGT.setText(goiTap.moTa)
        btnThem.setText("Cập nhật")
        //============================
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
            lifecycleScope.launchWhenCreated {
                viewModel.updateGoiTap(GoiTapModel(goiTap.maGT, txtTenGT.text.toString(), txtMoTa.text.toString(), txtTrangThai.text.toString(), idLoaiGT))
                viewModel.goiTaps.collect {
                    if (it.isEmpty()) {
                        return@collect
                    } else {
                        getDataGT("Success", "Fail")
                    }
                }
            }
            lifecycleScope.launchWhenCreated {
                viewModel.updateGia(GiaGtModel(gia.idGia, txtNgayAD.text.toString(), txtGiaGT.text.toString(), txtMaGT.text.toString(), maNV))
                viewModel.gias.collect {
                    if (it.isNotEmpty()) {
                        gias.addAll(it)
                        Log.d("TAGG", "GiaInsert: successful ${it.toString()}")

                    } else {
                        Log.d("TAGG", "GiaInsert: failed ${it.toString()}")
                        return@collect
                    }
                }
            }
        }
        //-------------------------------------------------------------

        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun getControl(imbCalendar: ImageButton, txtNgayAD: EditText) {
        txtNgayAD.setText(getCurrentDate())
        imbCalendar.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
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
        return
    }

    override fun itemClickDelete(maGT: String) {
        for (i in gias.indices) {
            if (maGT == gias[i].maGT) {
                viewModel.deleteGia(gias[i].idGia)
                lifecycleScope.launchWhenCreated {
                    viewModel.getDSGiaTheoGoiTap(maGT)
                    viewModel.goiTaps.collect {
                        if (it.isEmpty()) {
                            viewModel.deleteGoiTap(maGT)
                            Toast.makeText(requireContext(), "Delete gt success!", Toast.LENGTH_SHORT).show()
                        } else return@collect
                    }
                }
            }
        }
        getDataGT("Delete success", "Delete fail")
        return
    }
}

