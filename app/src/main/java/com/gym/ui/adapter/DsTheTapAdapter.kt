package com.gym.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.R
import com.gym.databinding.ItemThetapBinding
import com.gym.model.CtTheTapModel
import java.text.SimpleDateFormat
import java.util.*

class DsTheTapAdapter(private val _itemClick: OnItemClick): RecyclerView.Adapter<DsTheTapAdapter.ViewHolder>() {
    var ctTheTaps = mutableListOf<CtTheTapModel>()
    inner class ViewHolder(val binding: ItemThetapBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemThetapBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                ctThe = ctTheTaps[position]
                if(ctTheTaps[position].trangThai == "Khóa"){
                    itemEnable.isEnabled = false
                    runnn.visibility = View.GONE
                    itemEnable.setBackgroundResource(R.drawable.shape_center_panel_fail)
                }
                tvChiTiet.setOnClickListener {
                    _itemClick.itemClickInfo(ctTheTaps[position])
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return ctTheTaps.size
    }
    interface OnItemClick{
        fun itemClickInfo(theTapModel: CtTheTapModel)
    }
}