package com.gym.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemGoitapBinding
import com.gym.model.GoiTapModel
import com.gym.model.LoaiGtModel
import com.gym.ui.SingletonAccount
import com.gym.ui.viewmodel.ViewModel

class GoiTapAdapter(private val _itemClick: OnItemClick) : RecyclerView.Adapter<GoiTapAdapter.ViewHolder>() {
    var goiTaps = mutableListOf<GoiTapModel>()
    var loaiGTs = mutableListOf<LoaiGtModel>()

    inner class ViewHolder(val binding: ItemGoitapBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemGoitapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.apply {
                if(goiTaps[position].ctTheTaps != null){
                    imbDelete.visibility = View.GONE
                    imbEdit.visibility = View.GONE
                }
                if(SingletonAccount.taiKhoan?.maQuyen == "Q02"){
                    imbDelete.visibility = View.GONE
                    imbEdit.visibility = View.GONE
                }
                goiTap = goiTaps[position]
                for(i in loaiGTs.indices){
                    if(goiTaps[position].idLoaiGT == loaiGTs[i].idLoaiGT){
                        tvTenLGT.text = loaiGTs[i].tenLoaiGT
                    }
                }
                //tvTenLGT.text = goiTaps[position].idLoaiGT.toString().trim()
                imbEdit.setOnClickListener {
                    _itemClick.itemClickEdit(goiTaps[position])
                }
                imbDelete.setOnClickListener {
                    _itemClick.itemClickDelete(goiTaps[position].maGT)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return goiTaps.size
    }

    interface OnItemClick {
        fun itemClickEdit(goiTapModel: GoiTapModel)
        fun itemClickDelete(maGT: String)
    }
}