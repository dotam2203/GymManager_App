package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThongKeKhachHangModel(
    var maKH: String = "",
    var hoTen :  String = "",
    var tongTien: Long
) : Parcelable