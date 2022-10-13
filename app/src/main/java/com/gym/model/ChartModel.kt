package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChartModel(
    var donGia: String = "",
    var ngayDK: String = ""
) : Parcelable