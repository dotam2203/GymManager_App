package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BaiTapModel(
    var idBT: Int? = 0,
    var tenBT: String? = "",
    var moTa: String? = "",
    var duongDan : String? = ""
) : Parcelable