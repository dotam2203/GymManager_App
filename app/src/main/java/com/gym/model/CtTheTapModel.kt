package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CtTheTapModel(
    var idCTThe: Int = 0,
    var donGia: String = "",
    var maGT: String = "",
    var maHD: String = "",
    var maThe: String = "",

    var maKH: String = "",
    var tenGT: String = "",
    var tenLoaiGT: String = "",
    var trangThai: String = "",
    var ngayBD: String = "",
    var ngayKT: String = ""
) : Parcelable
