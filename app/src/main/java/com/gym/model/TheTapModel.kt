package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TheTapModel(
    var maThe: String = "",
    var ngayDK: String = "",
    var ngayBD: String = "",
    var ngayKT: String = "",
    var trangThai: String = "",
    var maKH: String = "",
    var hoaDons: List<HoaDonModel>? = null
) : Parcelable