package com.gym.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


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

    private val barChart get() = binding.barchart
    var listThe = ArrayList<TheTapModel>()

    ////

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThongkeBinding.inflate(layoutInflater)
        goiTaps = getDSGoiTap()

        binding.apply {
            txtNgBD.setText(getCurrentDate())
            txtNgBD.setText("01/01/2022")
            txtNgBD.focusable = 0
            txtNgKT.focusable = 0
            txtNgKT.setText(getCurrentDate())
            constraint.visibility = View.GONE
            showChartLayout(false)
            lifecycleScope.launchWhenCreated {
                khachHangs = getDSKhachHang()
                listThe = getDSTheTap()
                delay(1000L)
            }
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
        val options = arrayListOf(
            "Doanh thu theo tháng",
            "Doanh thu theo dịch vụ",
            "Top 10 khách hàng tiềm năng"
        )
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
                        binding.tvTDL.visibility = View.VISIBLE
                        binding.tvTongDT.visibility = View.VISIBLE
                        pbLoad.visibility = View.GONE
                        constraint.visibility = View.VISIBLE
                    }
                } else if (item == 2) {
                    showChartLayout(false)
                    //=================
                    col1.text = "STT"
                    col2.text = "Khách hàng"
                    //=================
                    pbLoad.visibility = View.VISIBLE
                    thongKes.clear()
                    lifecycleScope.launchWhenCreated {
                        //top10KHTiemNang(getFormatDateCompareTo(txtNgBD.text.toString().trim()),getFormatDateCompareTo(txtNgKT.text.toString().trim()))
                        setAdapterThongKe(list10customer())
                        delay(1000L)
                        showTableLayout(true)
                        pbLoad.visibility = View.GONE
                        checkList.visibility = View.GONE
                        binding.tvTDL.visibility = View.GONE
                        binding.tvTongDT.visibility = View.GONE
                    }
                }
            }
            btnChart.setOnClickListener {
//                if (item == 0) {
//                    if (constraint.visibility == View.VISIBLE) {
//                        constraint.visibility = View.GONE
//                    }
//                    showChartLayout(true)
//                    showTableLayout(false)
//                    createRandomBarGraph(
//                        txtNgBD.text.toString().trim(), txtNgKT.text.toString().trim()
//                    )
//                } else {
//                    showChartLayout(false)
//                }
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=Z2TTrIiv1hI")
                    )
                )

                Toast.makeText(context, "chua xong", Toast.LENGTH_SHORT).show()
            }
            floatingActionButton.setOnClickListener {
                if (item == 0) {
                    createRandomBarGraph(
                        txtNgBD.text.toString().trim(), txtNgKT.text.toString().trim()
                    )
                    barChart.notifyDataSetChanged()
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

    private fun initAdapter() {
        binding.apply {
            rvThongKe.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = thongKeAdapter
            }
        }
    }

    private fun createRandomBarGraph(ngayBD: String?, ngayKT: String?) {
        var dates: ArrayList<String>? = ArrayList()
        val barEntries: ArrayList<BarEntry> = ArrayList()
        try {

            dates = getList(ngayBD!!, ngayKT!!)
            for (j in dates.indices) {

                val tong = 0

                barEntries?.add(BarEntry(tong.toFloat(), j))
            }
            if (barEntries.isEmpty()) {
                showNoData(true)
            } else showNoData(false)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val barDataSet = BarDataSet(barEntries, "VND")
        val barData = BarData(dates, barDataSet)
        barChart?.data = barData
    }

//    fun getDataForChart(ngayBD: String, ngayKT: String): ArrayList<ChartModel> {
//        var sum: Long = 0
//        viewModel.getDSCtTheTapThang(ngayBD, ngayKT)
//        lifecycleScope.launchWhenCreated {
//            viewModel.ctTheTaps.collect {
//                if (it.isNotEmpty()) {
//                    binding.checkList.visibility = View.GONE
//                    for (i in it.indices) {
//
//
//                    }
//                } else {
//                    binding.apply {
//                        checkList.visibility = View.VISIBLE
//                        constraint.visibility = View.GONE
//                        showChartLayout(true)
//                    }
//                    return@collect
//                }
//            }
//        }
//
//    }

    private fun getList(startDate: String, endDate: String): ArrayList<String> {
        var listMonth = ArrayList<String>()
        val forMater: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val beginCalendar = Calendar.getInstance()
        val finishCalendar = Calendar.getInstance()

        try {
            beginCalendar.time = forMater.parse(getMonthYear(startDate)) as Date
            finishCalendar.time = forMater.parse(getMonthYear(endDate)) as Date
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        while (beginCalendar.before(finishCalendar) || beginCalendar.equals(finishCalendar)) {
            // add one month to date per loop
            val date: String = forMater.format(beginCalendar.time).toUpperCase()
//            Log.d("hungbt", date)
            beginCalendar.add(Calendar.MONTH, 1)
            listMonth.add(date)
        }

        return listMonth

    }

    fun showChartLayout(value: Boolean) {
        if (value) {
            if (binding.barchart.visibility == View.GONE && binding.floatingActionButton.visibility == View.GONE) {
                binding.barchart.visibility = View.VISIBLE
                binding.floatingActionButton.visibility = View.VISIBLE
            }
        } else {
            if (binding.barchart.visibility == View.VISIBLE && binding.floatingActionButton.visibility == View.VISIBLE) {
                binding.barchart.visibility = View.GONE
                binding.floatingActionButton.visibility = View.GONE
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

    fun showNoData(value: Boolean) {
        if (value) {
            if (binding.checkList.visibility == View.GONE) {
                binding.checkList.visibility = View.VISIBLE
            }
        } else {
            if (binding.checkList.visibility == View.VISIBLE) {
                binding.checkList.visibility = View.GONE
            }
        }
    }

    /***
     *  dd/mm/yyyy -> 01/mm/yyyy
     * */
    fun getMonthYear(date: String): String {
        val d: List<Any> = date.split("/")
        var year: String? = ""
        var month: String? = ""
        month = d[1].toString().trim()
        year = d[2].toString().trim()
        return "01/$month/$year"
    }

    /***
     *  get list 10 customer
     * */
    private fun list10customer(): ArrayList<ThongKeKhachHangModel> {
        val listKH: ArrayList<ThongKeKhachHangModel> = ArrayList()
        for (i in khachHangs.indices) {
            val temp = getListTheTheoKH(khachHangs[i].maKH)
            var sum: Long = 0
            for (j in temp.indices) {
                sum += temp[j].hoaDons?.get(j)?.ctTheTaps?.get(j)?.donGia?.toLong()!!
                val khachhang =
                    ThongKeKhachHangModel(khachHangs[i].maKH, khachHangs[i].hoTen, sum)
                listKH.add(khachhang)
            }
        }

        val list10KH: ArrayList<ThongKeKhachHangModel> = ArrayList()
        listKH.sortByDescending { it.tongTien }
        for (i in listKH.indices) {
            if (i >= 10) {
                break
            }
            list10KH.add(listKH[i])

        }

        list10KH.sortByDescending { it.tongTien }
        return list10KH

    }

    private fun getListTheTheoKH(maKH: String): ArrayList<TheTapModel> {
        val listTemp: ArrayList<TheTapModel> = ArrayList()
        for (i in listThe.indices) {
            if (listThe[i].maKH == maKH) {
                listTemp.add(listThe[i])
            }
        }
        return listTemp
    }

    private fun setAdapterThongKe(list: ArrayList<ThongKeKhachHangModel>) {
        binding.checkList.visibility = View.GONE
        thongKes.clear()
        thongKeAdapter.thongKes.clear()
        for (i in list.indices) {
            thongKes.add(
                ThongKeModel(
                    list[i].tongTien.toString(),
                    "",
                    list[i].hoTen,
                    ""
                )
            )
        }

        thongKeAdapter.thongKes.addAll(thongKes)
        thongKeAdapter.notifyDataSetChanged()
    }

}


