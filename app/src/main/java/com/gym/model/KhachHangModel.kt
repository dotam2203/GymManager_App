package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  19/07/2022
 */
@Parcelize
data class KhachHangModel(
    val maKH : String,
    val tenKH : String,
    val email : String,
    val sdt : String,
    val phai : String,
    val diaChi : String,
    val avatar : String,
    val idLoaiKH : Int
) : Parcelable
