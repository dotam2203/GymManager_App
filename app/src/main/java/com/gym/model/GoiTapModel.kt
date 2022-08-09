package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

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
