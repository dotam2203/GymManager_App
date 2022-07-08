package com.gym.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
@Parcelize
data class LoaiGTModel (
    val maLoaiGT: String,
    val tenLoaiGT: String,
    val trangThai: String,
) : Parcelable