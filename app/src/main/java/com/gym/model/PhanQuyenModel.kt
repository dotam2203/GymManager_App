package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class PhanQuyenModel(
    var maQuyen: String? = "",
    var tenQuyen: String? = "",
    var taiKhoans: List<TaiKhoanModel>? = null
) : Parcelable