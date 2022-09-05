package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThongKeModel(
    var donGia: String = "",
    var maGT: String = "",
    var tenGT: String = "",
    var ngayDK: String = ""
) : Parcelable