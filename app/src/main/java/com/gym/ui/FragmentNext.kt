package com.gym.ui

import android.view.View
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

}