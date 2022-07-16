package com.gym.ui.interfaces

import com.gym.model.LoaiGtModel
import java.util.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  15/07/2022
 */
interface OnItemClick {
    fun clickEditLoaiGT(loaiGtModel: LoaiGtModel){}
    fun clickDelete(id: Int){}
}