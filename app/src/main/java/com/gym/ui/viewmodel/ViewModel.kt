package com.gym.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.model.*
import com.gym.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
class ViewModel : ViewModel() {
    private val goiTapRepository = GoiTapRepository()
    private val khachHangRepository = KhachHangRepository()
    private val loaiKhRepository = LoaiKhRepository()
    private val loaiGtRepository = LoaiGtRepository()
    private val nhanVienRepository = NhanVienRepository()
    private val phanQuyenRepository = PhanQuyenRepository()
    private val taiKhoanRepository = TaiKhoanRepository()
    private val giaRepository = GiaGtRepository()
    //------------------------------Gói tập---------------------------------
    /*private val _uiNews = MutableStateFlow(T)
    val uiNews: StateFlow<T> = _uiNews*/

    //Live data
    private val _goiTaps = MutableStateFlow(emptyList<GoiTapModel>())
    val goiTaps: StateFlow<List<GoiTapModel>> = _goiTaps
    private val _goiTap: MutableStateFlow<GoiTapModel?> = MutableStateFlow(null)
    val goiTap: StateFlow<GoiTapModel?> = _goiTap

    fun getDSGoiTap() {
        viewModelScope.launch {
            goiTapRepository.getDSGoiTap().collect {
                if (it.isSuccessful) {
                    _goiTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSGoiTapTheoLoaiGT(idLoaiGT: Int) {
        viewModelScope.launch {
            goiTapRepository.getDSGoiTapTheoLoaiGT(idLoaiGT).collect {
                if (it.isSuccessful) {
                    _goiTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getGoiTap(maGT: String) {
        viewModelScope.launch {
            goiTapRepository.getGoiTap(maGT).collect {
                if (it.isSuccessful) {
                    _goiTap.value = it.body()
                }
            }
        }
    }

    fun insertGoiTapGia(goiTapModel: GoiTapModel, giaGtModel: GiaGtModel) {
        viewModelScope.launch {
            goiTapRepository.insertGoiTapGia(goiTapModel, giaGtModel).collect {
                if (it.isSuccessful) {
                    it.body()?.let {
                        _goiTap.value = it
                    }
                }
            }
        }
    }

    fun updateGoiTap(goiTapModel: GoiTapModel) {
        viewModelScope.launch {
            goiTapRepository.updateGoiTap(goiTapModel).collect {
                if (it.isSuccessful) {
                    _goiTap.value = it.body()
                }
            }
        }
    }

    fun deleteGoiTap(maGT: String) {
        viewModelScope.launch {
            goiTapRepository.deleteGoiTap(maGT)
        }
    }

    //-------------------------------------------------------------------------
    //----------------------------------Khách hàng-----------------------------
    //Live data
    private val _khachHangs = MutableStateFlow(emptyList<KhachHangModel>())
    val khachHangs: StateFlow<List<KhachHangModel>> = _khachHangs
    private val _khachHang: MutableStateFlow<KhachHangModel?> = MutableStateFlow(null)
    val khachHang: StateFlow<KhachHangModel?> = _khachHang

    fun getDSKhachHang() {
        viewModelScope.launch {
            khachHangRepository.getDSKhachHang().collect {
                if (it.isSuccessful) {
                    _khachHangs.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSKhachHangTheoLoaiKH(idLoaiKH: Int) {
        viewModelScope.launch {
            khachHangRepository.getDSKhachHangTheoLoaiKH(idLoaiKH).collect {
                if (it.isSuccessful) {
                    _khachHangs.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getKhachHang(maKH: String) {
        viewModelScope.launch {
            khachHangRepository.getKhachHang(maKH).collect {
                if (it.isSuccessful) {
                    _khachHang.value = it.body()
                }
            }
        }
    }

    fun insertKhachHang(KhachHangModel: KhachHangModel) {
        viewModelScope.launch {
            khachHangRepository.insertKhachHang(KhachHangModel).collect {
                if (it.isSuccessful) {
                    _khachHang.value = it.body()
                }
            }
        }
    }

    fun updateKhachHang(KhachHangModel: KhachHangModel) {
        viewModelScope.launch {
            khachHangRepository.updateKhachHang(KhachHangModel).collect {
                if (it.isSuccessful) {
                    _khachHang.value = it.body()
                }
            }
        }
    }

    fun deleteKhachHang(maKH: String) {
        viewModelScope.launch {
            khachHangRepository.deleteKhachHang(maKH)
        }
    }

    //-------------------------------------------------------------------------
    //----------------------------------Loại khách hàng------------------------
    //Live data
    private val _loaiKHs = MutableStateFlow(emptyList<LoaiKhModel>())
    val loaiKHs: StateFlow<List<LoaiKhModel>> = _loaiKHs
    private val _loaiKH: MutableStateFlow<LoaiKhModel?> = MutableStateFlow(null)
    val loaiKH: StateFlow<LoaiKhModel?> = _loaiKH

    fun getLoaiKH(idLoaiKH: Int) {
        viewModelScope.launch {
            loaiKhRepository.getLoaiKH(idLoaiKH).collect {
                if (it.isSuccessful) {
                    _loaiKH.value = it.body()
                }
            }
        }
    }

    fun getDSLoaiKH() {
        viewModelScope.launch {
            loaiKhRepository.getDSLoaiKH().collect {
                if (it.isSuccessful) {
                    _loaiKHs.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun insertLoaiKH(loaiKhModel: LoaiKhModel) {
        viewModelScope.launch {
            loaiKhRepository.insertLoaiKH(loaiKhModel).collect {
                if (it.isSuccessful) {
                    _loaiKH.value = it.body()
                }
            }
        }
    }

    fun updateLoaiKH(loaiKhModel: LoaiKhModel) {
        viewModelScope.launch {
            loaiKhRepository.updateLoaiKH(loaiKhModel).collect {
                if (it.isSuccessful) {
                    _loaiKH.value = it.body()
                }
            }
        }
    }

    fun deleteLoaiKH(id: Int) {
        viewModelScope.launch {
            loaiKhRepository.deleteLoaiKH(id)
        }
    }

    //-------------------------------------------------------------------------
    //----------------------------------Loại Gói Tập-----------------------------
    //Live data
    private val _loaiGTs = MutableStateFlow(emptyList<LoaiGtModel>())
    val loaiGTs: StateFlow<List<LoaiGtModel>> = _loaiGTs
    private val _loaiGT: MutableStateFlow<LoaiGtModel?> = MutableStateFlow(null)
    val loaiGT: StateFlow<LoaiGtModel?> = _loaiGT

    fun getLoaiGT(idLoaiGT: Int) {
        viewModelScope.launch {
            loaiGtRepository.getLoaiGT(idLoaiGT).collect {
                if (it.isSuccessful) {
                    _loaiGT.value = it.body()
                }
            }
        }
    }

    fun getDSLoaiGT() {
        viewModelScope.launch {
            loaiGtRepository.getDSLoaiGT().collect {
                if (it.isSuccessful) {
                    _loaiGTs.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun insertLoaiGT(loaiGtModel: LoaiGtModel) {
        viewModelScope.launch(Dispatchers.Main) {
            loaiGtRepository.insertLoaiGT(loaiGtModel).collect {
                if (it.isSuccessful) {
                    _loaiGT.value = it.body()
                }
            }
        }
    }

    fun updateLoaiGT(loaiGtModel: LoaiGtModel) {
        viewModelScope.launch {
            loaiGtRepository.updateLoaiGT(loaiGtModel).collect {
                if (it.isSuccessful) {
                    _loaiGT.value = it.body()
                }
            }
        }
    }

    fun deleteLoaiGT(id: Int) {
        viewModelScope.launch {
            loaiGtRepository.deleteLoaiGT(id)
        }
    }

    //-------------------------------------------------------------------------
    //----------------------------------Nhân viên-----------------------------
    //Live data
    private val _nhanViens = MutableStateFlow(emptyList<NhanVienModel>())
    val nhanViens: StateFlow<List<NhanVienModel>> = _nhanViens
    private val _nhanVien: MutableStateFlow<NhanVienModel?> = MutableStateFlow(null)
    val nhanVien: StateFlow<NhanVienModel?> = _nhanVien

    fun getNhanVien(maNV: String) {
        viewModelScope.launch {
            nhanVienRepository.getNhanVien(maNV).collect {
                if (it.isSuccessful) {
                    _nhanVien.value = it.body()
                }
            }
        }
    }

    fun getDSNhanVien() {
        viewModelScope.launch {
            nhanVienRepository.getDSNhanVien().collect {
                if (it.isSuccessful) {
                    _nhanViens.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun insertNhanVien(nhanVienModel: NhanVienModel) {
        viewModelScope.launch {
            nhanVienRepository.insertNhanVien(nhanVienModel).collect {
                if (it.isSuccessful) {
                    _nhanVien.value = it.body()
                }
            }
        }
    }

    fun updateNhanVien(nhanVienModel: NhanVienModel) {
        viewModelScope.launch {
            nhanVienRepository.updateNhanVien(nhanVienModel).collect {
                if (it.isSuccessful) {
                    _nhanVien.value = it.body()
                }
            }
        }
    }

    fun deleteNhanVien(maNV: String) {
        viewModelScope.launch {
            nhanVienRepository.deleteNhanVien(maNV)
        }
    }

    //-------------------------------------------------------------------------
    //----------------------------------Phân quyền-----------------------------
    //Live data
    private val _quyens = MutableStateFlow(emptyList<PhanQuyenModel>())
    val quyens: StateFlow<List<PhanQuyenModel>> = _quyens
    private val _quyen: MutableStateFlow<PhanQuyenModel?> = MutableStateFlow(null)
    val quyen: StateFlow<PhanQuyenModel?> = _quyen

    fun getQuyen(maQuyen: String) {
        viewModelScope.launch {
            phanQuyenRepository.getQuyen(maQuyen).collect {
                if (it.isSuccessful) {
                    _quyen.value = it.body()
                }
            }
        }
    }

    fun getDSQuyen() {
        viewModelScope.launch {
            phanQuyenRepository.getDSQuyen().collect {
                if (it.isSuccessful) {
                    _quyens.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun insertQuyen(phanQuyenModel: PhanQuyenModel) {
        viewModelScope.launch {
            phanQuyenRepository.insertQuyen(phanQuyenModel).collect {
                if (it.isSuccessful) {
                    _quyen.value = it.body()
                }
            }
        }
    }

    fun updateQuyen(phanQuyenModel: PhanQuyenModel) {
        viewModelScope.launch {
            phanQuyenRepository.updateQuyen(phanQuyenModel).collect {
                if (it.isSuccessful) {
                    _quyen.value = it.body()
                }
            }
        }
    }

    fun deleteQuyen(maNV: String) {
        viewModelScope.launch {
            phanQuyenRepository.deleteQuyen(maNV)
        }
    }

    //------------------------------------------------------------------------
    //------------------------------Tài khoản---------------------------------
    //Live data
    private val _taiKhoans = MutableStateFlow(emptyList<TaiKhoanModel>())
    val taiKhoans: StateFlow<List<TaiKhoanModel>> = _taiKhoans
    private val _taiKhoan: MutableStateFlow<TaiKhoanModel?> = MutableStateFlow(null)
    val taiKhoan: StateFlow<TaiKhoanModel?> = _taiKhoan

    fun getDSTaiKhoan() {
        viewModelScope.launch {
            taiKhoanRepository.getDSTaiKhoan().collect {
                if (it.isSuccessful)
                    _taiKhoans.value = it.body() ?: emptyList()
            }
        }
    }

    fun getDSTaiKhoanTheoQuyen(maQuyen: String) {
        viewModelScope.launch {
            taiKhoanRepository.getDSTaiKhoanTheoQuyen(maQuyen).collect {
                if (it.isSuccessful) {
                    _taiKhoans.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSTaiKhoanTheoNhanVien(maNV: String) {
        viewModelScope.launch {
            taiKhoanRepository.getDSTaiKhoanTheoQuyen(maNV).collect {
                if (it.isSuccessful) {
                    _taiKhoans.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getTaiKhoan(maTK: String) {
        viewModelScope.launch {
            taiKhoanRepository.getTaiKhoan(maTK).collect {
                if (it.isSuccessful) {
                    _taiKhoan.value = it.body()
                }
            }
        }
    }

    fun insertTaiKhoan(taiKhoanModel: TaiKhoanModel) {
        viewModelScope.launch {
            taiKhoanRepository.insertTaiKhoan(taiKhoanModel).collect {
                if (it.isSuccessful) {
                    _taiKhoan.value = it.body()
                }
            }
        }
    }

    fun updateTaiKhoan(taiKhoanModel: TaiKhoanModel) {
        viewModelScope.launch {
            taiKhoanRepository.updateTaiKhoan(taiKhoanModel).collect {
                if (it.isSuccessful) {
                    _taiKhoan.value = it.body()
                }
            }
        }
    }

    fun deleteTaiKhoan(maTK: String) {
        viewModelScope.launch {
            taiKhoanRepository.deleteTaiKhoan(maTK)
        }
    }

    //------------------------------------------------------------------------
    //------------------------------Giá Gói Tập---------------------------------
    //Live data
    private val _gias = MutableStateFlow(emptyList<GiaGtModel>())
    val gias: StateFlow<List<GiaGtModel>> = _gias
    private val _gia: MutableStateFlow<GiaGtModel?> = MutableStateFlow(null)
    val gia: StateFlow<GiaGtModel?> = _gia

    fun getDSGia() {
        viewModelScope.launch {
            giaRepository.getDSGia().collect {
                if (it.isSuccessful) {
                    _gias.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSGiaTheoGoiTap(maGT: String) {
        viewModelScope.launch {
            giaRepository.getDSGiaTheoGoiTap(maGT).collect {
                if (it.isSuccessful) {
                    _gias.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSGiaTheoNhanVien(maNV: String) {
        viewModelScope.launch {
            giaRepository.getDSGiaTheoNhanVien(maNV).collect {
               // it.body()?.orEmpty()
                it.body().orEmpty()
                if (it.isSuccessful) {
                    _gias.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getGia(idGia: Int) {
        viewModelScope.launch {
            giaRepository.getGia(idGia).collect {
                if (it.isSuccessful) {
                    _gia.value = it.body()
                }
            }
        }
    }

    fun insertGia(giaGtModel: GiaGtModel) {
        viewModelScope.launch {
            giaRepository.insertGia(giaGtModel).collect {
                if (it.isSuccessful) {
                    _gia.value = it.body()
                }
            }
        }
    }

    fun updateGia(giaGtModel: GiaGtModel) {
        viewModelScope.launch {
            giaRepository.updateGia(giaGtModel).collect {
                if (it.isSuccessful) {
                    _gia.value = it.body()
                }
            }
        }
    }

    fun deleteGia(idGia: Int) {
        viewModelScope.launch {
            giaRepository.deleteGia(idGia)
        }
    }
//==============================================PASS DATA===========================================

    //==================================================================================================
    private fun replaceString(s: String): String {
        var sToiUu = s
        sToiUu = sToiUu.trim()
        val arrWord = sToiUu.split(" ");
        sToiUu = ""
        for (word in arrWord) {
            var newWord = word.lowercase(Locale.getDefault())
            if (newWord.isNotEmpty()) {
                newWord = newWord.replaceFirst((newWord[0] + ""), (newWord[0] + "").uppercase(Locale.getDefault()))
                sToiUu += "$newWord "
            }
        }
        return sToiUu.trim()
    }

    fun randomMaNV(s: String, ma: String): String {
        val sRandom = replaceString(s)
        var str = ""
        val wordArr = sRandom.split(" ")
        for (word in wordArr) {
            if (word.isNotEmpty()) {
                str += word[0].toString()
            }
        }
        val str1: Int = ma.substring(2).toInt()
        if (str1 < 10) {
            str = str.plus("0")
            str = str.plus(str1 + 1)
        } else if (str1 >= 10) {
            str = str.plus(str1 + 1)
        }
        return str.trim()
    }
    fun formatMoney(money: String): String{
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0;
        val convert = numberFormat.format(money.trim().toInt())
        return convert.substring(1)
    }
}