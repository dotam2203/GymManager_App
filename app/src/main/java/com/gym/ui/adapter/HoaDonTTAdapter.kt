package com.gym.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemHoadonBinding
import com.gym.model.HoaDonModel

class HoaDonTTAdapter (private val _itemClick: OnItemClick) : RecyclerView.Adapter<HoaDonTTAdapter.ViewHolder>() {
    var hoaDons = mutableListOf<HoaDonModel>()
    var row_index = -1
    inner class ViewHolder(val binding: ItemHoadonBinding): RecyclerView.ViewHolder(binding.root){
    }

    fun setFilterList(filter: ArrayList<HoaDonModel>){
        this.hoaDons = filter
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHoadonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                row_index = position
                tvMaHD.text = hoaDons[position].maHD
                tvNgayLap.text = getFormatDateFromAPI(hoaDons[position].ngayLap)
                tvHoaDonKH.text = hoaDons[position].tenKH
                imbInfo.setOnClickListener {
                    _itemClick.itemClickInfo(hoaDons[position])
                    if(row_index==position){
                        imbInfo.setBackgroundColor(Color.parseColor("#567845"));
                    }
                    else
                    {
                        imbInfo.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }

            }
        }
    }
    override fun getItemCount(): Int {
        return hoaDons.size
    }
    interface OnItemClick{
        fun itemClickInfo(hoaDonModel: HoaDonModel)
    }
    private fun getFormatDateFromAPI(date: String): String {
        val d: List<Any> = date.split("-")
        var year: String? = ""
        var month: String? = ""
        var day: String? = ""
        day = d[2].toString().trim()
        month = d[1].toString().trim()
        year = d[0].toString().trim()
        return "$day/$month/$year"
    }
}