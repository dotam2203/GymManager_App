package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GiaGtModel(
    var idGia: Int?= 0,
    var ngayAD: String?= "",
    var gia: String?= "",
    var maGT: String?= "",
    var maNV: String?= ""
) : Parcelable