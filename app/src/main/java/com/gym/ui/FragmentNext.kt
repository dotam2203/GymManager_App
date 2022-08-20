package com.gym.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.gym.R
import com.gym.ui.viewmodel.ViewModel
import kotlinx.coroutines.delay
import pl.droidsonroids.gif.GifImageView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  06/07/2022
 */
abstract class FragmentNext : Fragment() {
    val viewModel: ViewModel by lazy {
        ViewModelProvider(this)[ViewModel::class.java]
    }
    fun callBack(id: Int){
        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(id)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callBack)
    }
    fun getFragment(view: View, id: Int) {
        Navigation.findNavController(view).navigate(id)
    }

    fun replaceFragment(id: Int, fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(id, fragment).commit();
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        return currentDate.toString().trim()
    }
    fun getFormatDate(date: String): String {
        val d: List<Any> = date.split("-")
        var year: String? = ""
        var month: String? = ""
        var day: String? = ""
        day = d[2].toString().trim()
        month = d[1].toString().trim()
        year = d[0].toString().trim()
        val dateFormat = "$day/$month/$year"
        return dateFormat
    }

    fun getFormatDateApi(date: String): String {
        val d: List<Any> = date.split("/")
        var year: String? = ""
        var month: String? = ""
        var day: String? = ""
        day = d[0].toString().trim()
        month = d[1].toString().trim()
        year = d[2].toString().trim()
        val dateFormat = "$year-$month-$day"
        return dateFormat
    }

    fun formatCurrentMoney(number: Double): String {
        val localID = Locale("IND", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localID)
        val formatCurrent: String = numberFormat.format(number)
        val split: List<String> = formatCurrent.split(",")
        val length: Int = split[0].length
        return "${split[0].substring(0, 2)}.${split[0].substring(2, length)}"
    }

    fun getDataSpinner(spinner: AutoCompleteTextView, listSP: List<Any>): String {
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, listSP)
        var selectItem: String = ""
        spinner.setAdapter(arrayAdapter)
        spinner.setOnItemClickListener { parent, view, position, id ->
            selectItem = parent.getItemAtPosition(position).toString()
        }
        return selectItem
    }
    fun replaceString(s: String): String {
        var sToiUu = s
        sToiUu = sToiUu.trim()
        val arrWord = sToiUu.split(" ");
        sToiUu = ""
        for (word in arrWord) {
            var newWord = word.lowercase(Locale.getDefault())
            if (newWord.isNotEmpty()) {
                newWord = newWord.replaceFirst((newWord[0] + ""), (newWord[0] + "").uppercase(Locale.getDefault()))
                sToiUu += "$newWord "
            }
        }
        return sToiUu.trim()
    }

    fun randomString(s: String, ma: String): String {
        val sRandom = replaceString(s)
        var str = ""
        val wordArr = sRandom.split(" ")
        for (word in wordArr) {
            if (word.isNotEmpty()) {
                str += word[0].toString()
            }
        }
        val str1: Int = ma.trim().substring(2).toInt()
        if (str1 < 9) {
            str = str.plus("0")
            str = str.plus(str1 + 1)
        }
        else {
            str = str.plus(str1 + 1)
        }
        return str.trim()
    }
    fun getRandomMaHD(): String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        return "HD${current.format(formatter)}"
    }
    fun getDateTime(): String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        return current.format(formatter)
    }
    fun formatMoney(money: String): String {
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0;
        return numberFormat.format(money.toInt()).substring(1)
    }
    fun getSoLuongLoaiGT(tenLoaiGT: String): ArrayList<String>{
        val slDangKy = ArrayList<String>()
        var sl: String = ""
        when(tenLoaiGT){
            "Ngày" -> {
                for( i in 1..6){
                    sl = "$i ngày"
                    slDangKy.add(sl)
                }
            }
            "Tuần" -> {
                for( i in 1..3){
                    sl = "$i tuần"
                    slDangKy.add(sl)
                }
            }
            "Tháng" -> {
                for( i in 1..11){
                    sl = "$i tháng"
                    slDangKy.add(sl)
                }
            }
            "Năm" -> {
                for( i in 1..5){
                    sl = "$i năm"
                    slDangKy.add(sl)
                }
            }
            else -> arrayListOf<Any>("")

        }
        return slDangKy
    }
    //--------------------------------------------
    fun dialogPopMessage(msg: String, drawable: Int){
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.pop_message)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes : WindowManager.LayoutParams = window!!.attributes
        windowAtrributes.gravity = Gravity.CENTER
        window.attributes = windowAtrributes
        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        dialog.setCancelable(false)
        dialog.show()
        val imvGif: GifImageView = dialog.findViewById(R.id.imvGif)
        val tvPopMessage: TextView = dialog.findViewById(R.id.tvPopMessage)
        imvGif.setImageResource(drawable) //R.drawable.ic_done
        tvPopMessage.text = msg
        lifecycleScope.launchWhenCreated {
            delay(3000L)
            dialog.dismiss()
        }

    }
    fun getTenLoaiGT(id: Int): String{
        var tenLoaiGT: String  = ""
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect{
                if(it.isNotEmpty()){
                    for(i in it.indices){
                        if( id == it[i].idLoaiGT){
                            tenLoaiGT = it[i].tenLoaiGT
                        }
                    }
                }
                else
                    return@collect
            }
        }
        return tenLoaiGT
    }
    fun getTenQuyenByMaQuyen(maQuyen: String): String{
        var tenQ: String = ""
        viewModel.getQuyen(maQuyen)
        lifecycleScope.launchWhenCreated {
            viewModel.quyen.collect{
                if(it != null){
                    tenQ = it.tenQuyen
                }
                else return@collect
            }
        }
        return tenQ
    }
}