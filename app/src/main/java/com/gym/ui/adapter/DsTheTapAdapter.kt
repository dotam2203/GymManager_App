package com.gym.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemThetapBinding
import com.gym.model.TheTapModel

class DsTheTapAdapter: RecyclerView.Adapter<DsTheTapAdapter.ViewHolder>() {
    var theTaps = listOf<TheTapModel>()
    inner class ViewHolder(val binding: ItemThetapBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemThetapBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                tvTenDVDK.text = theTaps[position].trangThai
            }
        }
    }

    override fun getItemCount(): Int {
        return theTaps.size
    }
}