package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class TaiKhoanModel(
    var maTK:  String? = null,
    var matKhau:  String? = null,
    var trangThai:  String? = null,
    var maQuyen:  String? = null,
    var maNV:  String? = null,
) : Parcelable
