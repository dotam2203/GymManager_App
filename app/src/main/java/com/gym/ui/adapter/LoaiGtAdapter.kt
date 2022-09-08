package com.gym.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemLoaigtBinding
import com.gym.model.LoaiGtModel
import com.gym.ui.SingletonAccount

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  08/07/2022
 */
class LoaiGtAdapter(private val _itemClick: OnItemClick): RecyclerView.Adapter<LoaiGtAdapter.ViewHolder>(){
    var loaiGTs = mutableListOf<LoaiGtModel>()
    inner class ViewHolder(val binding: ItemLoaigtBinding): RecyclerView.ViewHolder(binding.root) {
        /*fun jj(flag : Int) {
            if(flag == 0){
                binding.imbDelete.visibility = View.VISIBLE
                binding.imbEdit.visibility = View.VISIBLE
            }
            else if(flag == 1 || SingletonAccount.taiKhoan?.maQuyen == "Q02"){
                binding.imbDelete.visibility = View.GONE
                binding.imbEdit.visibility = View.GONE
            }
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemLoaigtBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                if(SingletonAccount.taiKhoan!!.maQuyen == "Q02"){
                    imbDelete.visibility = View.GONE
                    imbEdit.visibility = View.GONE
                }
                else if(SingletonAccount.taiKhoan!!.maQuyen == "Q01"){
                    if(loaiGTs[position].goiTaps.isNullOrEmpty() ){
                        imbDelete.visibility = View.VISIBLE
                        imbEdit.visibility = View.VISIBLE
                    }
                    else {
                        imbDelete.visibility = View.GONE
                        imbEdit.visibility = View.GONE
                    }
                }
                loaiGT = loaiGTs[position]
                imbEdit.setOnClickListener {
                    _itemClick.itemClickEdit(loaiGTs[position])
                }
                imbDelete.setOnClickListener {
                    _itemClick.itemClickDelete(loaiGTs[position].idLoaiGT)
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return loaiGTs.size
    }
    interface OnItemClick{
        fun itemClickEdit(loaiGtModel: LoaiGtModel)
        fun itemClickDelete(id: Int)
    }
}