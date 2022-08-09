package com.gym.ui

import android.app.Dialog
import android.text.Editable
import android.text.TextUtils.substring
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.gym.R
import com.gym.model.LoaiGtModel
import com.gym.ui.viewmodel.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  06/07/2022
 */
abstract class FragmentNext : Fragment() {
    val viewModel: ViewModel by lazy {
        ViewModelProvider(this)[ViewModel::class.java]
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

    fun setFormatCurrentMoney(money: EditText) {
        money.addTextChangedListener(object : TextWatcher {
            var setTxt: String = money.text.toString().trim()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != setTxt) {
                    money.removeTextChangedListener(this)
                    val replace: String = s.toString().replace("", "")
                    if (!replace.isEmpty()) {
                        setTxt = formatCurrentMoney(replace.toDouble())
                    } else {
                        setTxt = ""
                    }
                    money.apply {
                        setText(setTxt)
                        setSelection(setTxt.length)
                        //addTextChangedListener(this@FragmentNext)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
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
    fun formatMoney(money: String): String{
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0;
        val convert = numberFormat.format(money.trim().toInt())
        return convert.substring(1)
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
    /*fun dialog(){
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_loaigt)

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
        var btnHuy: Button = dialog.findViewById(R.id.btnHuy)
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }*/
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
}