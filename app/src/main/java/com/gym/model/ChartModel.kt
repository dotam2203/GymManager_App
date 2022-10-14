package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChartModel(
    var donGia: String = "",
    var ngayDK: String = ""
) : Parcelable

@Parcelize
data class ChartModelDV(
    var donGia: String = "",
    var tenDV: String = ""
) : Parcelable