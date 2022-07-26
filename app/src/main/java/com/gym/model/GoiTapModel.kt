package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  15/07/2022
 */
@Entity
@Parcelize
data class GoiTapModel(
    var maGT: String = "",
    var tenGT: String = "",
    var moTa: String = "",
    var trangThai: String = "",
    var idLoaiGT: Int = 0,
    var goiTaps: List<GiaGtModel>? = null
): Parcelable
