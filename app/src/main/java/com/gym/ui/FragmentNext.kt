package com.gym.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  06/07/2022
 */
abstract class FragmentNext : Fragment(){
    fun getFragment(view: View, id: Int ) {
        Navigation.findNavController(view).navigate(id)
    }
    fun replaceFragment(id : Int,fragment: Fragment ) {
        childFragmentManager.beginTransaction().replace(id, fragment).commit();
    }
    fun getCurrentDate(): String{
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        return currentDate.toString().trim()
    }
    fun setFormatCurrentMoney(money: EditText){
        money.addTextChangedListener(object : TextWatcher{
            var setTxt: String = money.text.toString().trim()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() != setTxt){
                    money.removeTextChangedListener(this)
                    val replace : String = s.toString().replace("","")
                    if(!replace.isEmpty()){
                        setTxt = formatCurrentMoney(replace.toDouble())
                    }
                    else{
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
    fun formatCurrentMoney(number: Double): String{
        val localID = Locale("IND","ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localID)
        val formatCurrent: String = numberFormat.format(number)
        val split: List<String> = formatCurrent.split(",")
        val length: Int = split[0].length
        return "${split[0].substring(0,2)}.${split[0].substring(2,length)}"
    }
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

}