package com.gym.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemDichvuBinding
import com.gym.model.LoaiGTModel
import java.util.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  08/07/2022
 */
class LoaiGtAdapter(private var loaiGTs: List<LoaiGTModel>): RecyclerView.Adapter<LoaiGtAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemDichvuBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDichvuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.loaiGT = loaiGTs[position]
        }
        //holder.binding.loaiGT = loaiGTs[position]
    }

    override fun getItemCount(): Int {
        return loaiGTs.size
    }
    fun updateData(loaiGTss: List<LoaiGTModel>){
        loaiGTs = loaiGTss
        notifyDataSetChanged()
    }
}