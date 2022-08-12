package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CtKhuyenMaiModel(
    var idCTKM: Int = 0,
    var phanTramGiam: Double = 0.0,
    var maGT: String = "",
    var idKM: Int = 0
) : Parcelable
