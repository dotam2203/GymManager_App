package com.gym.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemKhachhangBinding
import com.gym.model.KhachHangModel
import com.gym.model.LoaiKhModel
import com.gym.ui.SingletonAccount

class KhachHangAdapter(private val _itemClick: OnItemClick) : RecyclerView.Adapter<KhachHangAdapter.ViewHolder>() {
    var khachHangs = listOf<KhachHangModel>()
    var loaiKHs = listOf<LoaiKhModel>()
    inner class ViewHolder(val binding: ItemKhachhangBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemKhachhangBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                if((khachHangs[position].theTaps) != null || (SingletonAccount.taiKhoan?.maQuyen == "Q02")){
                    imbDelete.visibility = View.GONE
                }
                khachHang = khachHangs[position]
                imbEdit.setOnClickListener {
                    _itemClick.itemClickEdit(khachHangs[position])
                }
                imbDelete.setOnClickListener {
                    _itemClick.itemClickDelete(khachHangs[position].maKH)
                }
                itemClickKH.setOnClickListener{
                    _itemClick.itemLongClick(khachHangs[position])
                }
                for(i in loaiKHs.indices){
                    if(khachHangs[position].idLoaiKH == loaiKHs[i].idLoaiKH){
                        tvLoaiKH.text = loaiKHs[i].tenLoaiKH
                        break
                    }
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return khachHangs.size
    }
    fun updateData(khachHangs: List<KhachHangModel>){
        this.khachHangs = khachHangs
        notifyDataSetChanged()
    }
    interface OnItemClick{
        fun itemClickEdit(khachHangModel: KhachHangModel)
        fun itemClickDelete(maKH: String)
        fun itemLongClick(khachHangModel: KhachHangModel)
    }
}