package com.gym.ui

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.gym.R
import com.gym.databinding.DialogLoadingBinding

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  08/07/2022
 */
open class DialogLoad(myFragment: Fragment){
    var fragment: Fragment
    lateinit var dialog: AlertDialog

    init {
        fragment = myFragment
    }
    fun starLoadingDialog(){
        var builder = AlertDialog.Builder(fragment.context)
        val inflater = fragment.layoutInflater
        builder.apply {
            setView(inflater.inflate(R.layout.dialog_loading,null))
            setCancelable(true)
        }
        dialog = builder.create()
        dialog.show()
    }
    fun dismissialog(){
        dialog.dismiss()
    }
}