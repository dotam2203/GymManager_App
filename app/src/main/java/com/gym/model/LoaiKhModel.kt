package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  16/07/2022
 */
@Entity
@Parcelize
data class LoaiKhModel(
    var idLoaiKH: Int = 0,
    var tenLoaiKH: String = "",
    var khachHangs: List<KhachHangModel>? = null
) : Parcelable