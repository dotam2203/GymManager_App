package com.gym.ui.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  06/09/2022
 */
class DatePicker2Fragment: DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val calendar = Calendar.getInstance()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //dafault date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        //return new DatePickerDialog instance
        val datePickerDialog = DatePickerDialog(requireActivity(), this, year, month, day)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        return datePickerDialog
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        //format date
        val selectDate = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(calendar.time)
        //pass data
        val passDate = Bundle()
        passDate.putString("PASS_DATE", selectDate)
        setFragmentResult("REQUEST_KEY",passDate)
    }

}