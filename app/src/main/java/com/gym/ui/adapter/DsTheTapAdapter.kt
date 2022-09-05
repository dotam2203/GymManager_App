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

class DsTheTapAdapter(val _itemClick: OnItemClick): RecyclerView.Adapter<DsTheTapAdapter.ViewHolder>() {
    var ctTheTaps = listOf<CtTheTapModel>()
    inner class ViewHolder(val binding: ItemThetapBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemThetapBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                if(position % 2 == 0){
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
                else if(position % 2 != 0){
                    itemEnable.visibility = View.GONE
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