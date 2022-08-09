package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CtBaiTapModel(
    var idCTBT: Int? = 0,
    var idBT: Int? = 0,
    var maGT: String? = ""
) : Parcelable
