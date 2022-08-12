package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KhuyenMaiModel(
    var idKM: Int? = 0,
    var ngayBD: String = "",
    var ngayKT: String = "",
    var moTa: String = "",
    var maNV: String = "",
    var ctKhuyenMais: List<CtKhuyenMaiModel>? = null
) : Parcelable
