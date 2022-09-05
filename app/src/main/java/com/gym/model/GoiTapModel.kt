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
    var tenLoaiGT: String = "",
    var giaGoiTaps: List<GiaGtModel>? = null,
    var ctTheTaps: List<CtTheTapModel>? = null,
    var ctKhuyenMais: List<CtKhuyenMaiModel>? = null,
    var ctBaiTaps: List<CtBaiTapModel>? = null
): Parcelable
