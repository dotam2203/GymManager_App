package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  15/07/2022
 */
@Entity
@Parcelize
data class GoiTapModel(
    val maGoiTap: String,
    val tenGoiTap: String,
    val moTa: String,
    val trangThai: String
): Parcelable
