package com.gym.ui.adapter

import android.view.LayoutInflater
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
                if(compareToDate(ctTheTaps[position].ngayBD)){
                    tvTenDVDK.text = ctTheTaps[position].trangThai
                    ctThe = ctTheTaps[position]
                    tvChiTiet.setOnClickListener {
                        _itemClick.itemClickInfo(ctTheTaps[position])
                    }
                }
                else{
                    tvTenDVDK.text = "Khóa"
                    ctThe = ctTheTaps[position]
                    itemEnable.isEnabled = false
                    itemEnable.setBackgroundResource(R.drawable.shape_center_panel_fail)
                }
                //ctThe = ctTheTaps[position]
            }
        }
    }

    override fun getItemCount(): Int {
        return ctTheTaps.size
    }
    fun compareToDate(dateStart: String): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date()).toString().trim()
        val date1 = sdf.parse(currentDate)
        val date2 = sdf.parse(dateStart)
        if(date1.compareTo(date2) == 0)
            return true
        else if(date1 < date2)
            return true
        else if(date1 > date2)
            return false
        return false
    }
    interface OnItemClick{
        fun itemClickInfo(theTapModel: CtTheTapModel)
    }
}