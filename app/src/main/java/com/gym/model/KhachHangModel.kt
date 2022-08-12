package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class KhachHangModel(
    var maKH :  String = "",
    var hoTen :  String = "",
    var email :  String = "",
    var sdt :  String = "",
    var phai :  String = "",
    var diaChi :  String = "",
    var hinhAnh :  String = "",
    var idLoaiKH : Int = 0,
    var theTaps: List<TheTapModel>? = null
) : Parcelable
