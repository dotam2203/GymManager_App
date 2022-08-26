package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class TaiKhoanModel(
    var maTK:  String = "",
    var matKhau:  String = "",
    var trangThai:  String = "",
    var maQuyen:  String = "",
    var maNV:  String = "",
    var email:  String = ""
) : Parcelable
