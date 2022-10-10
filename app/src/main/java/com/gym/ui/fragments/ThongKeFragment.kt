package com.gym.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.gym.R
import com.gym.databinding.FragmentThongkeBinding
import com.gym.model.*
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount
import com.gym.ui.adapter.ThongKeAdapter
import kotlinx.coroutines.delay
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ThongKeFragment : FragmentNext() {
    private lateinit var binding: FragmentThongkeBinding
    var thongKeAdapter = ThongKeAdapter()
    var goiTaps = ArrayList<GoiTapModel>()
    var thongKes = ArrayList<ThongKeModel>()
    var khachHangs = ArrayList<KhachHangModel>()
    var thes = ArrayList<TheTapModel>()
    var ctThe = CtTheTapModel()
    var item = 0

    ////
    private var barEntries: ArrayList<BarEntry>? = ArrayList()
    private val barChart get() = binding.barchart
    private var dates: ArrayList<String>? = ArrayList()

    ////

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThongkeBinding.inflate(layoutInflater)
        goiTaps = getDSGoiTap()
        khachHangs = getDSKhachHang()
        binding.apply {
            txtNgBD.setText(getCurrentDate())
            txtNgKT.setText(getCurrentDate())
            constraint.visibility = View.GONE
            chartLayout.visibility = View.GONE

        }
        getEvent()
        initAdapter()
        if (SingletonAccount.taiKhoan?.maQuyen == "Q02") {
            binding.btnLoc.isEnabled = false
        } else if (SingletonAccount.taiKhoan?.maQuyen == "Q01") {
            binding.btnLoc.isEnabled = true
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setControl()
    }

    private fun setControl() {
        val options = arrayListOf("Doanh thu theo tháng", "Doanh thu theo dịch vụ")
        Log.e("option", "setControl: $options")
        binding.apply {
            item = 0
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, options)
            spDV.setAdapter(arrayAdapter)
            spDV.setOnItemClickListener { parent, view, position, id ->
                item = position
            }
        }
    }

    private fun getEvent() {
        binding.apply {
            txtNgBD.setOnClickListener {
                val datePickerFragment = DatePicker2Fragment()
                val support = requireActivity().supportFragmentManager
                //receive date
                support.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("PASS_DATE")
                        txtNgBD.setText(date)
                    }
                }
                //show dialog
                datePickerFragment.show(support, "DatePickerFragment")
            }
            txtNgKT.setOnClickListener {
                val datePickerFragment = DatePicker2Fragment()
                val support = requireActivity().supportFragmentManager
                //receive date
                support.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("PASS_DATE")
                        txtNgKT.setText(date)
                    }
                }
                //show dialog
                datePickerFragment.show(support, "DatePickerFragment")
                constraint.visibility = View.GONE
            }
            btnLoc.setOnClickListener {
                showChartLayout(false)
                thongKeAdapter.flag = item
                if (item == 0) {
                    //=================
                    col1.text = "Tháng"
                    //=================
                    pbLoad.visibility = View.VISIBLE
                    thongKes.clear()
                    lifecycleScope.launchWhenCreated {
                        thongKeTheoThang(
                            getFormatDateCompareTo(txtNgBD.text.toString().trim()),
                            getFormatDateCompareTo(txtNgKT.text.toString().trim())
                        )
                        delay(1000L)
                        pbLoad.visibility = View.GONE
                        constraint.visibility = View.VISIBLE
                    }
                } else if (item == 1) {
                    showChartLayout(false)
                    //=================
                    col1.text = "Loại DV"
                    //=================
                    pbLoad.visibility = View.VISIBLE
                    thongKes.clear()
                    Log.e(
                        "NgayBD",
                        "NgayBD: ${getFormatDateCompareTo(txtNgBD.text.toString().trim())} "
                    )
                    Log.e(
                        "NgayKT",
                        "NgayKT: ${getFormatDateCompareTo(txtNgKT.text.toString().trim())}"
                    )
                    lifecycleScope.launchWhenCreated {
                        thongKeTheoDichVu(
                            getFormatDateCompareTo(txtNgBD.text.toString().trim()),
                            getFormatDateCompareTo(txtNgKT.text.toString().trim())
                        )
                        delay(1000L)
                        pbLoad.visibility = View.GONE
                        constraint.visibility = View.VISIBLE
                    }
                } else if (item == 2) {
                    showChartLayout(false)
                    //=================
                    col1.text = "Hạng"
                    col2.text = "Khách hàng"
                    //=================
                    pbLoad.visibility = View.VISIBLE
                    thongKes.clear()
                    lifecycleScope.launchWhenCreated {
                        //top10KHTiemNang(getFormatDateCompareTo(txtNgBD.text.toString().trim()),getFormatDateCompareTo(txtNgKT.text.toString().trim()))
                        delay(1000L)
                        pbLoad.visibility = View.GONE
                        checkList.visibility = View.VISIBLE
                        constraint.visibility = View.GONE
                    }
                }
            }
            btnChart.setOnClickListener {
                if (item == 0) {
                    if (constraint.visibility == View.VISIBLE) {
                        constraint.visibility = View.GONE
                    }
                    showChartLayout(true)
                    showTableLayout(false)
                    createRandomBarGraph(
                        getFormatDateCompareTo(txtNgBD.text.toString().trim()),
                        getFormatDateCompareTo(txtNgKT.text.toString().trim())
                    )
                } else {
                    showChartLayout(false)
                }

            }
            floatingActionButton.setOnClickListener {
                if (item == 0) {
                    createRandomBarGraph(
                        getFormatDateCompareTo(txtNgBD.text.toString().trim()),
                        getFormatDateCompareTo(txtNgKT.text.toString().trim())
                    )
                    barChart?.notifyDataSetChanged()
                    Toast.makeText(context, "Touch The Chart To RESET", Toast.LENGTH_SHORT)
                        .show()

                }

            }

        }
    }

    private fun thongKeTheoThang(ngayBD: String, ngayKT: String) {
        viewModel.getDSCtTheTapThang(ngayBD, ngayKT)
        lifecycleScope.launchWhenCreated {
            viewModel.ctTheTaps.collect {
                if (it.isNotEmpty()) {
                    binding.checkList.visibility = View.GONE
                    thongKes.clear()
                    var sum: Long = 0
                    thongKeAdapter.thongKes.clear()
                    for (i in it.indices) {
                        thongKes.add(
                            ThongKeModel(
                                it[i].donGia,
                                it[i].maGT,
                                it[i].tenGT,
                                it[i].ngayDK
                            )
                        )
                        sum += tongDoanhThu(it[i].donGia)
                    }
                    thongKeAdapter.thongKes.addAll(thongKes)
                    thongKeAdapter.notifyDataSetChanged()
                    binding.tvTongDT.text = "${formatMoney(sum.toString().trim())} đ"
                } else {
                    binding.apply {
                        checkList.visibility = View.VISIBLE
                        constraint.visibility = View.GONE
                    }
                    return@collect
                }
            }
        }
    }

    private fun thongKeTheoDichVu(ngayBD: String, ngayKT: String) {
        viewModel.getDSCtTheTapTheoDV(ngayBD, ngayKT)
        lifecycleScope.launchWhenCreated {
            viewModel.ctTheTaps.collect {
                if (it.isNotEmpty()) {
                    thongKes.clear()
                    var sum: Long = 0
                    thongKeAdapter.thongKes.clear()
                    for (i in it.indices) {
                        thongKes.add(
                            ThongKeModel(
                                it[i].donGia,
                                it[i].maGT,
                                it[i].tenGT,
                                it[i].ngayDK
                            )
                        )
                        sum += tongDoanhThu(it[i].donGia)
                    }
                    thongKeAdapter.thongKes.addAll(thongKes)
                    thongKeAdapter.notifyDataSetChanged()
                    binding.tvTongDT.text = "${formatMoney(sum.toString().trim())} đ"
                } else {
                    return@collect
                }
            }
        }
    }

    private fun top10KHTiemNang(ngayBD: String, ngayKT: String) {
        var sum: Long = 0
        for (i in khachHangs.indices) {
            thes = getDSTheTheoKH(khachHangs[i].maKH)
            for (j in thes.indices) {
                getCtTheTheoTheTap(thes[i].maThe)
                sum += tongDoanhThu(ctThe.donGia)
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            rvThongKe.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = thongKeAdapter
            }
        }
    }

    private fun createRandomBarGraph(ngayBD: String?, ngayKT: String?) {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")

        try {
            val date1 = ngayBD?.let { simpleDateFormat.parse(it) }
            val date2 = ngayKT?.let { simpleDateFormat.parse(it) }
            val mDate1 = Calendar.getInstance()
            val mDate2 = Calendar.getInstance()
            if (date1 != null) {
                mDate1.time = date1
            }
            if (date2 != null) {
                mDate2.time = date2
            }

            dates = getList(mDate1, mDate2)
            for (j in dates!!.indices) {
                val tong: Long = getSumDataForChart(ngayBD!!, ngayKT!!)
                barEntries?.add(BarEntry(tong.toFloat(), j))
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val barDataSet = BarDataSet(barEntries, "VND")
        val barData = BarData(dates, barDataSet)
        barChart?.data = barData
    }

    fun getSumDataForChart(ngayBD: String, ngayKT: String): Long {
        var sum: Long = 0
        viewModel.getDSCtTheTapThang(ngayBD, ngayKT)
        lifecycleScope.launchWhenCreated {
            viewModel.ctTheTaps.collect {
                if (it.isNotEmpty()) {
                    binding.checkList.visibility = View.GONE
                    for (i in it.indices) {
                        sum += tongDoanhThu(it[i].donGia)
                    }
                } else {
                    binding.apply {
                        checkList.visibility = View.VISIBLE
                        constraint.visibility = View.GONE
                        showChartLayout(true)
                    }
                    return@collect
                }
            }
        }

        return sum
    }

    private fun getDate(cld: Calendar): String {
        var curDate = (cld[Calendar.DAY_OF_MONTH].toString() + "/" + (cld[Calendar.MONTH] + 1)
                + "/" + cld[Calendar.YEAR])
        try {
            val date = SimpleDateFormat("dd/MM/yyyy").parse(curDate)
            curDate = SimpleDateFormat("dd/MM/yyyy").format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return curDate
    }

    private fun getList(startDate: Calendar, endDate: Calendar?): ArrayList<String> {
        val list = ArrayList<String>()
//        while (endDate?.let { startDate.compareTo(it) }!! <= 0) {
//            list.add(getDate(startDate))
//            startDate.add(Calendar.DAY_OF_MONTH, 1)
//        }

        while (startDate.before(endDate)) {
            list.add(getDate(startDate))
            startDate.add(Calendar.DAY_OF_MONTH, 1)
        }
        return list
    }

    fun showChartLayout(value: Boolean) {
        if (value) {
            if (binding.chartLayout.visibility == View.GONE) {
                binding.chartLayout.visibility = View.VISIBLE
            }
        } else {
            if (binding.chartLayout.visibility == View.VISIBLE) {
                binding.chartLayout.visibility = View.GONE
            }
        }


    }

    fun showTableLayout(value: Boolean) {
        if (value) {
            if (binding.constraint.visibility == View.GONE) {
                binding.constraint.visibility = View.VISIBLE
            }
        } else {
            if (binding.constraint.visibility == View.VISIBLE) {
                binding.constraint.visibility = View.GONE
            }
        }


    }

}


