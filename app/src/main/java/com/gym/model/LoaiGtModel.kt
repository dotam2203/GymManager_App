package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
@Entity
@Parcelize
data class LoaiGtModel (
    val idLoaiGT: Int,
    val tenLoaiGT: String,
    val trangThai: String,
) : Parcelable