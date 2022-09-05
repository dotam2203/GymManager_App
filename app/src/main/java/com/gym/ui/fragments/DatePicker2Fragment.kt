package com.gym.ui.fragments

import android.app.DatePickerDialog
import androidx.fragment.app.DialogFragment
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.setFragmentResult
import java.text.SimpleDateFormat
import java.util.*

class DatePicker2Fragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val calendar = Calendar.getInstance()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //dafault date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        //return new DatePickerDialog instance
        return DatePickerDialog(requireActivity(), this, year, month, day)
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