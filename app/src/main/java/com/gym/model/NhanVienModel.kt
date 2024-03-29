package com.gym.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class NhanVienModel(
    var maNV: String= "",
    var hoTen: String= "",
    var email: String= "",
    var sdt: String= "",
    var phai: String= "",
    var diaChi: String= "",
    var hinhAnh: String= "",
    var giaGoiTaps: List<GiaGtModel>? = null,
    var khuyenMais: List<KhuyenMaiModel>? = null,
    var hoaDons: List<HoaDonModel>? = null
) : Parcelable
