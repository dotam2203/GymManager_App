package com.gym.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemGoitapBinding
import com.gym.model.GoiTapModel
import com.gym.ui.SingletonAccount

class GoiTapAdapter(private val _itemClick: OnItemClick) : RecyclerView.Adapter<GoiTapAdapter.ViewHolder>() {
    var goiTaps = mutableListOf<GoiTapModel>()

    inner class ViewHolder(val binding: ItemGoitapBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemGoitapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.apply {
                if(SingletonAccount.taiKhoan!!.maQuyen == "Q02"){
                    imbDelete.visibility = View.GONE
                }
                else if(SingletonAccount.taiKhoan!!.maQuyen == "Q01"){
                    if(goiTaps[position].ctTheTaps.isNullOrEmpty() ){
                        imbDelete.visibility = View.VISIBLE
                        imbEdit.visibility = View.VISIBLE
                    }
                    else {
                        imbDelete.visibility = View.GONE
                    }
                }
                goiTap = goiTaps[position]
                imbEdit.setOnClickListener {
                    _itemClick.itemClickEdit(goiTaps[position])
                }
                imbDelete.setOnClickListener {
                    _itemClick.itemClickDelete(goiTaps[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return goiTaps.size
    }

    interface OnItemClick {
        fun itemClickEdit(goiTapModel: GoiTapModel)
        fun itemClickDelete(goiTapModel: GoiTapModel)
    }
}