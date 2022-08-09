package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CtKhuyenMaiModel(
    var idCTKM: Int? = 0,
    var phanTramGiam: Float,
    var maGT: String? = "",
    var idKM: Int? = 0
) : Parcelable
