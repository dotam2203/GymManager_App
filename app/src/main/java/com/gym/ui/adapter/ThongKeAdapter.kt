package com.gym.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gym.databinding.ItemThongkeBinding
import com.gym.model.ThongKeModel
import com.gym.ui.FragmentNext
import java.text.NumberFormat

class ThongKeAdapter : RecyclerView.Adapter<ThongKeAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemThongkeBinding): RecyclerView.ViewHolder(binding.root){}
    var thongKes = mutableListOf<ThongKeModel>()
    var flag = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemThongkeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                if(position %2 == 0){
                    binding.tvItemTK.setBackgroundColor(Color.WHITE)
                }
                else if(position %2 != 0){
                    binding.tvItemTK.setBackgroundColor(Color.CYAN)
                }
                if(flag == 0){
                    tvDTThang.text = getMonthYear(thongKes[position].ngayDK)
                    //tvDTDichVu.text = thongKes[position].tenGT
                    tvDTDichVu.visibility = View.GONE
                    tvDoanhThu.text = "${formatMoney(thongKes[position].donGia)} đ"
                }
                else if(flag == 1){
                    tvDTDichVu.visibility = View.VISIBLE
                    tvDTThang.text = "${position + 1}"
                    tvDTDichVu.text = thongKes[position].tenGT
                    tvDoanhThu.text = "${formatMoney(thongKes[position].donGia)} đ"
                }else if(flag == 2){
                    tvDTDichVu.visibility = View.VISIBLE
                    tvDTThang.text = "${position + 1}"
                    tvDTDichVu.text = thongKes[position].tenGT
                    tvDoanhThu.text = "${formatMoney(thongKes[position].donGia)} đ"
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return thongKes.size
    }
    private fun getMonthYear(date: String): String {
        val d: List<Any> = date.split("-")
        var year: String? = ""
        var month: String? = ""
        var day: String? = ""
        month = d[1].toString().trim()
        year = d[0].toString().trim()
        return "$month/$year"
    }
    fun formatMoney(money: String): String {
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(money.toInt()).substring(1)
    }

}