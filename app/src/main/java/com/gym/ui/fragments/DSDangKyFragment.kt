package com.gym.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gym.databinding.FragmentDsDangkyBinding
import com.gym.model.CtTheTapModel
import com.gym.model.KhachHangModel
import com.gym.model.TheTapModel
import com.gym.ui.FragmentNext
import com.gym.ui.adapter.DsTheTapAdapter
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

class DSDangKyFragment : FragmentNext(), DsTheTapAdapter.OnItemClick {
    private lateinit var binding: FragmentDsDangkyBinding
    var khachHang = KhachHangModel()
    var ctTheTapAdapter = DsTheTapAdapter(this@DSDangKyFragment)
    var ctTheTaps = ArrayList<CtTheTapModel>()
    var ctTheTap = CtTheTapModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDsDangkyBinding.inflate(layoutInflater)
        refreshData()
        initAdapter()
        reviceDataKH()
        binding.pbLoad.visibility = View.VISIBLE
        lifecycleScope.launchWhenCreated {
            delay(1000L)
            if (khachHang.maKH != "") {
                initViewModel(khachHang)
            } else {
                binding.checkList.visibility = View.VISIBLE
                binding.pbLoad.visibility = View.GONE
            }
        }
        return binding.root
    }

    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                ctTheTapAdapter.notifyDataSetChanged()
                binding.pbLoad.visibility = View.GONE
                refresh.isRefreshing = false
            }
        }
    }

    private fun reviceDataKH() {
        parentFragmentManager.setFragmentResultListener("passData1", this, object : FragmentResultListener {
            override fun onFragmentResult(requestKey: String, result: Bundle) {
                khachHang = result.getParcelable("dataKH") ?: return
                initViewModel(khachHang)
            }
        })
    }

    fun initViewModel(khachHang: KhachHangModel) {
        binding.pbLoad.visibility = View.VISIBLE
        lifecycleScope.launchWhenCreated {
            delay(1000L)
            viewModel.getDSTheTapTheoKH(khachHang.maKH)
            lifecycleScope.launchWhenCreated {
                delay(1000L)
                viewModel.theTaps.collect {
                    if (it.isNotEmpty()) {
                         /*for (i in it.indices) {
                             if (compareToDate(getFormatDateFromAPI1(it[i].ngayBD)))
                                 viewModel.updateTheTap(TheTapModel(it[i].maThe, it[i].ngayDK, it[i].ngayBD, it[i].ngayKT, "Hoạt động", it[i].maKH))
                             else if (!compareToDate(getFormatDateFromAPI1(it[i].ngayBD)) || !compareToDate(getFormatDateFromAPI1(it[i].ngayKT))) {
                                 viewModel.updateTheTap(TheTapModel(it[i].maThe, it[i].ngayDK, it[i].ngayBD, it[i].ngayKT, "Khóa", it[i].maKH))
                             }
                         }*/
                        /*it.forEach { tt ->
                            val sdf = SimpleDateFormat("dd/MM/yyyy")
                            val dateBD: Date = sdf.parse(getFormatDateFromAPI(tt.ngayBD))
                            val dateKT: Date = sdf.parse(getFormatDateFromAPI(tt.ngayKT))
                            val date: Date = sdf.parse(getCurrentDate())
                            when {
                                (date.after(dateBD) && date.before(dateKT)) -> {
                                    viewModel.updateTheTap(TheTapModel(tt.maThe, tt.ngayDK, tt.ngayBD, tt.ngayKT, "Hoạt động", tt.maKH))
                                }
                                (date == dateBD && date == dateKT) -> {
                                    viewModel.updateTheTap(TheTapModel(tt.maThe, tt.ngayDK, tt.ngayBD, tt.ngayKT, "Hoạt động", tt.maKH))
                                }
                                else -> viewModel.updateTheTap(TheTapModel(tt.maThe, tt.ngayDK, tt.ngayBD, tt.ngayKT, "Khóa", tt.maKH))
                            }
                        }*/
                        ctTheTapAdapter.ctTheTaps.clear()
                        it.forEach { item ->
                            item.hoaDons?.forEach { hd ->
                                ctTheTapAdapter.ctTheTaps.addAll(hd.ctTheTaps ?: emptyList())
                            }
                        }
                        delay(500L)
                        ctTheTapAdapter.notifyDataSetChanged()
                        Log.e("TAG", "List ctthe = ${ctTheTapAdapter.ctTheTaps.size}")
                        binding.pbLoad.visibility = View.GONE
                        binding.checkList.visibility = View.GONE
                    } else {
                        ctTheTapAdapter.ctTheTaps.clear()
                        ctTheTapAdapter.notifyDataSetChanged()
                        binding.checkList.visibility = View.VISIBLE
                        binding.pbLoad.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            //pbLoad.visibility = View.VISIBLE
            rvTheTap.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = ctTheTapAdapter
            }
        }
    }

    override fun itemClickInfo(ctTheTap: CtTheTapModel) {
        viewModel.getCtTheTap(ctTheTap.idCTThe)
        lifecycleScope.launchWhenCreated {
            viewModel.ctTheTap.collect {
            }
        }
    }
}