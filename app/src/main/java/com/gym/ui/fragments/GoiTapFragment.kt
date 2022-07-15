package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import com.gym.R
import com.gym.databinding.FragmentGoitapBinding

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  14/07/2022
 */
class GoiTapFragment: Fragment() {
    private lateinit var binding: FragmentGoitapBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoitapBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imbAdd.setOnClickListener {
                dialog()
            }
        }
    }
    fun dialog(){
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_goitap)

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
        /*var btnHuy: Button = dialog.findViewById(R.id.btnHuy)
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }*/
    }
}