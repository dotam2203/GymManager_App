package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
@Parcelize
data class LoaiGtModel (
    var idLoaiGT: Int = 0,
    var tenLoaiGT: String = "",
    var trangThai: String = "",
    var goiTaps: List<GoiTapModel>? = null
) : Parcelable