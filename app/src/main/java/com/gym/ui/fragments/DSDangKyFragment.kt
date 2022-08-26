package com.gym.ui.fragments

import android.os.Bundle
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
            delay(2000L)
            reviceDataKH()
            if(khachHang.maKH != ""){
                binding.pbLoad.visibility = View.GONE
            }
            else binding.pbLoad.visibility = View.GONE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    fun reviceDataKH() {
        parentFragmentManager.setFragmentResultListener("passData", this, object : FragmentResultListener {
            override fun onFragmentResult(requestKey: String, result: Bundle) {
                khachHang = result.getParcelable("dataKH") ?: return
                initViewModel(khachHang)
            }
        })
    }

    fun initViewModel(khachHang: KhachHangModel) {
        binding.pbLoad.visibility = View.VISIBLE
        viewModel.getDSTheTapTheoKH(khachHang.maKH)
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            viewModel.theTaps.collect {
                if (it.isNotEmpty()) {
                    for(i in it.indices){
                        ctTheTaps.add(getCTTheByMaThe(it[i].maThe))
                    }
                    lifecycleScope.launchWhenCreated {
                        delay(2000L)

                        ctTheTapAdapter.ctTheTaps = ctTheTaps
                        initAdapter()
                        ctTheTapAdapter.notifyDataSetChanged()
                        binding.pbLoad.visibility = View.GONE
                    }
                } else {
                    binding.pbLoad.visibility = View.GONE
                    ctTheTapAdapter.notifyDataSetChanged()
                    return@collect
                }
            }
        }
    }
    fun getCTTheByMaThe(maThe: String): CtTheTapModel{
        var ctTheTap = CtTheTapModel()
        viewModel.getDSCtTheTapTheoThe(maThe)
        lifecycleScope.launchWhenCreated {
            viewModel.ctTheTaps.collect{
                if(it.isNotEmpty()){
                    ctTheTap = it[0]
                }
                else return@collect
            }
        }
        return ctTheTap
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