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
    val idKH: Int,
    val tenLoaiKH: String
) : Parcelable