package com.gym.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.gym.R

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  06/07/2022
 */
abstract class FragmentNext : Fragment(){
    fun getFragment(view: View, id : Int ) {
        Navigation.findNavController(view).navigate(id)
    }
    fun replaceFragment(id : Int,fragment: Fragment ) {
        childFragmentManager.beginTransaction().replace(id, fragment).commit();
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