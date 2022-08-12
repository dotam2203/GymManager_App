package com.gym.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemKhachhangBinding
import com.gym.databinding.ItemNhanvienBinding
import com.gym.model.NhanVienModel

class NhanVienAdapter(private val _itemClick: OnItemClick) : RecyclerView.Adapter<NhanVienAdapter.ViewHolder>() {
    var nhanViens = listOf<NhanVienModel>()
    inner class ViewHolder(val binding: ItemNhanvienBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNhanvienBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                nhanVien = nhanViens[position]
                imbEdit.setOnClickListener {
                    _itemClick.itemClickEdit(nhanViens[position])
                }
                imbDelete.setOnClickListener {
                    _itemClick.itemClickDelete(nhanViens[position].maNV.toString())
                }
                itemClickNV.setOnClickListener{
                    _itemClick.itemLongClick(nhanViens[position])
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return nhanViens.size
    }
    fun updateData(nhanViens: List<NhanVienModel>){
        this.nhanViens = nhanViens
        notifyDataSetChanged()
    }
    interface OnItemClick{
        fun itemClickEdit(nhanVienModel: NhanVienModel)
        fun itemClickDelete(maNV: String)
        fun itemLongClick(nhanVienModel: NhanVienModel)
    }
}