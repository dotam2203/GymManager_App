package com.gym.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemLoaikhBinding
import com.gym.model.LoaiKhModel
import com.gym.ui.SingletonAccount

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  08/07/2022
 */
class LoaiKhAdapter(val _itemClick: OnItemClick): RecyclerView.Adapter<LoaiKhAdapter.ViewHolder>(){
    var loaiKHs = listOf<LoaiKhModel>()
    inner class ViewHolder(val binding: ItemLoaikhBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemLoaikhBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                if(loaiKHs[position].khachHangs != null){
                    imbDelete.visibility = View.GONE
                    imbEdit.isEnabled = false
                }
                if(SingletonAccount.taiKhoan?.maQuyen == "Q02"){
                    imbDelete.visibility = View.GONE
                    imbEdit.visibility = View.GONE
                }
                loaiKH = loaiKHs[position]
                imbEdit.setOnClickListener {
                    _itemClick.itemClickEdit(loaiKHs[position])
                }
                imbDelete.setOnClickListener {
                    _itemClick.itemClickDelete(loaiKHs[position].idLoaiKH)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return loaiKHs.size
    }
    fun updateData(loaiKHs: List<LoaiKhModel>){
        this.loaiKHs = loaiKHs
        notifyDataSetChanged()
    }
    interface OnItemClick{
        fun itemClickEdit(loaiKhModel: LoaiKhModel)
        fun itemClickDelete(id: Int)
    }
}