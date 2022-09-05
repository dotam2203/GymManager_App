package com.gym.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlinx.coroutines.flow.collect

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
        binding.pbLoad.visibility = View.VISIBLE
        lifecycleScope.launchWhenCreated {
            delay(1000L)
            reviceDataKH()
            if(khachHang.maKH != ""){
                initViewModel(khachHang)
                initAdapter()
                //binding.pbLoad.visibility = View.GONE
            }
            else binding.pbLoad.visibility = View.GONE
        }
        return binding.root
    }

    private fun refreshData() {
        binding.apply {
            refresh.setOnRefreshListener {
                reviceDataKH()
                initAdapter()
                binding.pbLoad.visibility = View.GONE
                refresh.isRefreshing = false
            }
        }
    }

    private fun reviceDataKH() {
        parentFragmentManager.setFragmentResultListener("passData", this, object : FragmentResultListener {
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

                    if(it.isNotEmpty()){
                        for(i in it.indices){
                            if(compareToDate(it[i].ngayBD)){
                                viewModel.updateTheTap(TheTapModel(it[i].maThe,it[i].ngayDK,it[i].ngayBD,it[i].ngayKT,"Hoạt động",it[i].maKH))
                            }
                            else if(!compareToDate(it[i].ngayBD)){
                                viewModel.updateTheTap(TheTapModel(it[i].maThe,it[i].ngayDK,it[i].ngayBD,it[i].ngayKT,"Khóa",it[i].maKH))
                            }
                        }
                        ctTheTapAdapter.ctTheTaps = getCTTheByMaThe(it as ArrayList<TheTapModel>)
                        //initAdapter()
                        ctTheTapAdapter.notifyDataSetChanged()
                        Log.e("TAG", "List ctthe = ${ctTheTapAdapter.ctTheTaps.size}")
                        //Toast.makeText(requireActivity(), "List ctthe = ${ctTheTaps.size}", Toast.LENGTH_SHORT).show()
                        binding.pbLoad.visibility = View.GONE
                    }
                    else{
                        binding.pbLoad.visibility = View.GONE
                        ctTheTapAdapter.notifyDataSetChanged()
                        Toast.makeText(requireActivity(), "List null!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun getCTTheByMaThe(theTaps: ArrayList<TheTapModel>): ArrayList<CtTheTapModel>{
        val ctts = ArrayList<CtTheTapModel>()
        for(i in theTaps.indices){
            viewModel.getCtTheTapTheoThe(theTaps[i].maThe)
            lifecycleScope.launchWhenCreated {
                viewModel.ctTheTap.collect{
                    if(it != null){
                        ctts.add(it)
                    }
                    else return@collect
                }
            }
        }
        return ctts
    }
    private fun initAdapter() {
        binding.apply {
            pbLoad.visibility = View.VISIBLE
            rvTheTap.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
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