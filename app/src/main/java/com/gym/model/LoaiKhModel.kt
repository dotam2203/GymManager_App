package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class LoaiKhModel(
    var idLoaiKH: Int = 0,
    var tenLoaiKH: String = "",
    var khachHangs: List<KhachHangModel>? = null
) : Parcelable