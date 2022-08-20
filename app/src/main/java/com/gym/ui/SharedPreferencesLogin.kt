package com.gym.ui

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesLogin(context: Context?) {
    //fileName
    private val PREF_NAME = "SharedPreferences"

    val sharedPreferences: SharedPreferences? = context?.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

    fun setUser(user: String){
        editor?.apply {
            putString("user",user)
            commit()
        }
    }
    fun setPass(pass: String){
        editor?.apply {
            putString("pass",pass)
            commit()
        }
    }
    fun getUser(): String?{
        return sharedPreferences?.getString("user","")
    }
    fun getPass(): String?{
        return sharedPreferences?.getString("pass","")
    }
    fun removeData(){
        editor?.apply {
            clear()
            commit()
        }
    }

}