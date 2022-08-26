package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HoaDonModel(
    var maHD: String = "",
    var ngayLap: String = "",
    var maNV: String = "",
    var maThe: String = "",
    var tenKH: String = "",
    var ctTheTaps: List<CtTheTapModel>? = null
) : Parcelable