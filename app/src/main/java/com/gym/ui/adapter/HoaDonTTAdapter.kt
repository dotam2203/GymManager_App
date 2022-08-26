package com.gym.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemHoadonBinding
import com.gym.model.HoaDonModel

class HoaDonTTAdapter (private val _itemClick: OnItemClick) : RecyclerView.Adapter<HoaDonTTAdapter.ViewHolder>() {
    var hoaDons = listOf<HoaDonModel>()
    inner class ViewHolder(val binding: ItemHoadonBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHoadonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                tvMaHD.text = hoaDons[position].maHD
                tvNgayLap.text = hoaDons[position].ngayLap
                tvHoaDonKH.text = hoaDons[position].tenKH
                imbInfo.setOnClickListener {
                    _itemClick.itemClickInfo(hoaDons[position])
                }
                imbSelect.setOnClickListener {
                    _itemClick.itemClickSelect(hoaDons[position].maHD)
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return hoaDons.size
    }
    fun updateData(hoaDons: List<HoaDonModel>){
        this.hoaDons = hoaDons
        notifyDataSetChanged()
    }
    interface OnItemClick{
        fun itemClickInfo(hoaDonModel: HoaDonModel)
        fun itemClickSelect(maHD: String)
    }
}