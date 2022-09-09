package com.gym.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.model.*
import com.gym.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

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
    private val hoaDonRepository = HoaDonRepository()
    private val theTapRepository = TheTapRepository()
    private val baiTapRepository = BaiTapRepository()
    private val khuyenMaiRepository = KhuyenMaiRepository()
    private val ctKhuyenMaiRepository = CtKhuyenMaiRepository()
    private val ctBaiTapRepository = CtBaiTapRepository()
    private val ctTheTapRepository = CtTheTapRepository()
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
                    getDSGoiTap()
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
                getDSGoiTap()
            }
        }
    }

    fun deleteGoiTap(goiTap: GoiTapModel) {
        viewModelScope.launch {
            if(goiTap.giaGoiTaps.isNullOrEmpty()){
                goiTapRepository.deleteGoiTap(goiTap.maGT)
            }
            getDSGoiTap()
        }
    }

    //-------------------------------------------------------------------------
    //---------------------------------- Khách hàng -----------------------------
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

    fun insertKhachHang(khachHangModel: KhachHangModel) {
        viewModelScope.launch {
            khachHangRepository.insertKhachHang(khachHangModel).collect {
                if (it.isSuccessful) {
                    _khachHang.value = it.body()
                }
            }
        }
    }

    fun updateKhachHang(khachHangModel: KhachHangModel) {
        viewModelScope.launch {
            khachHangRepository.updateKhachHang(khachHangModel).collect {
                if (it.isSuccessful) {
                    _khachHang.value = it.body()
                }
            }
        }
    }

    fun deleteKhachHang(maKH: String) {
        viewModelScope.launch {
            khachHangRepository.deleteKhachHang(maKH)
            getDSKhachHang()
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
                getDSLoaiKH()
            }
        }
    }

    fun updateLoaiKH(loaiKhModel: LoaiKhModel) {
        viewModelScope.launch {
            loaiKhRepository.updateLoaiKH(loaiKhModel).collect {
                if (it.isSuccessful) {
                    _loaiKH.value = it.body()
                }
                getDSLoaiKH()
            }
        }
    }

    fun deleteLoaiKH(id: Int) {
        viewModelScope.launch {
            loaiKhRepository.deleteLoaiKH(id)
            getDSLoaiKH()
        }
    }

    //-------------------------------------------------------------------------
    //----------------------------------Loại Gói Tập-----------------------------
    //Live data
    val listLoaiGT = ArrayList<LoaiGtModel>()
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
                getDSLoaiGT()
            }
        }
    }

    fun updateLoaiGT(loaiGtModel: LoaiGtModel) {
        viewModelScope.launch {
            loaiGtRepository.updateLoaiGT(loaiGtModel).collect {
                if (it.isSuccessful) {
                    _loaiGT.value = it.body()
                }
                getDSLoaiGT()
            }
        }
    }

    fun deleteLoaiGT(id: Int) {
        viewModelScope.launch {
            if(loaiGTs.value.isNotEmpty()){
                loaiGtRepository.deleteLoaiGT(id)
                getDSLoaiGT()
            }
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
                getDSNhanVien()
            }
        }
    }

    fun updateNhanVien(nhanVienModel: NhanVienModel) {
        viewModelScope.launch {
            nhanVienRepository.updateNhanVien(nhanVienModel).collect {
                if (it.isSuccessful) {
                    _nhanVien.value = it.body()
                }
                getDSNhanVien()
            }
        }
    }

    fun deleteNhanVien(maNV: String) {
        viewModelScope.launch {
            nhanVienRepository.deleteNhanVien(maNV)
            getDSNhanVien()
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
                getDSQuyen()
            }
        }
    }

    fun updateQuyen(phanQuyenModel: PhanQuyenModel) {
        viewModelScope.launch {
            phanQuyenRepository.updateQuyen(phanQuyenModel).collect {
                if (it.isSuccessful) {
                    _quyen.value = it.body()
                }
                getDSQuyen()
            }
        }
    }

    fun deleteQuyen(maNV: String) {
        viewModelScope.launch {
            phanQuyenRepository.deleteQuyen(maNV)
            getDSQuyen()
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
                getDSTaiKhoan()
            }
        }
    }

    fun updateTaiKhoan(taiKhoanModel: TaiKhoanModel) {
        viewModelScope.launch {
            taiKhoanRepository.updateTaiKhoan(taiKhoanModel).collect {
                if (it.isSuccessful) {
                    _taiKhoan.value = it.body()
                }
                getDSTaiKhoan()
            }
        }
    }

    fun deleteTaiKhoan(maTK: String) {
        viewModelScope.launch {
            taiKhoanRepository.deleteTaiKhoan(maTK)
            getDSTaiKhoan()
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
                getDSGia()
            }
        }
    }

    fun updateGia(giaGtModel: GiaGtModel) {
        viewModelScope.launch {
            giaRepository.updateGia(giaGtModel).collect {
                if (it.isSuccessful) {
                    _gia.value = it.body()
                }
                getDSGia()
            }
        }
    }

    fun deleteGiaGoiTap(idGia: Int,maGT: String) {
        viewModelScope.launch {
            giaRepository.deleteGiaGoiTap(idGia, maGT)
            getDSGia()
            getDSGoiTap()
        }
    }
    //---------------------------------------------------------------------
    //----------------------------------Hóa đơn-----------------------------
    //Live data
    private val _hoaDons = MutableStateFlow(emptyList<HoaDonModel>())
    val hoaDons: StateFlow<List<HoaDonModel>> = _hoaDons
    private val _hoaDon: MutableStateFlow<HoaDonModel?> = MutableStateFlow(null)
    val hoaDon: StateFlow<HoaDonModel?> = _hoaDon

    fun getDSHoaDon() {
        viewModelScope.launch {
            hoaDonRepository.getDSHoaDon().collect {
                if (it.isSuccessful) {
                    _hoaDons.value = it.body() ?: emptyList()
                }
            }
        }
    }
    fun getDSHoaDonTheoNgayGiam() {
        viewModelScope.launch {
            hoaDonRepository.getDSHoaDonTheoNgayGiam().collect {
                if (it.isSuccessful) {
                    _hoaDons.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSHoaDonTheoNV(maNV: String) {
        viewModelScope.launch {
            hoaDonRepository.getDSHoaDonTheoNV(maNV).collect {
                if (it.isSuccessful) {
                    _hoaDons.value = it.body() ?: emptyList()
                }
            }
        }
    }
    fun getDSHoaDonTheoThe(maThe: String) {
        viewModelScope.launch {
            hoaDonRepository.getDSHoaDonTheoThe(maThe).collect {
                if (it.isSuccessful) {
                    _hoaDons.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getHoaDon(maHD: String) {
        viewModelScope.launch {
            hoaDonRepository.getHoaDon(maHD).collect {
                if (it.isSuccessful) {
                    _hoaDon.value = it.body()
                }
            }
        }
    }

    fun insertHoaDon(hoaDonModel: HoaDonModel) {
        viewModelScope.launch {
            hoaDonRepository.insertHoaDon(hoaDonModel).collect {
                if (it.isSuccessful) {
                    _hoaDon.value = it.body()
                }
            }
        }
    }

    fun updateHoaDon(hoaDonModel: HoaDonModel) {
        viewModelScope.launch {
            hoaDonRepository.updateHoaDon(hoaDonModel).collect {
                if (it.isSuccessful) {
                    _hoaDon.value = it.body()
                }
            }
        }
    }

    fun deleteHoaDon(maHD: String) {
        viewModelScope.launch {
            hoaDonRepository.deleteHoaDon(maHD)
            getDSHoaDon()
        }
    }
    //-------------------------------------------------------------------------
    //---------------------------------- Thẻ tập -----------------------------
    //Live data
    private val _theTaps = MutableStateFlow(emptyList<TheTapModel>())
    val theTaps: StateFlow<List<TheTapModel>> = _theTaps
    private val _theTap: MutableStateFlow<TheTapModel?> = MutableStateFlow(null)
    val theTap: StateFlow<TheTapModel?> = _theTap

    fun getDSTheTap() {
        viewModelScope.launch {
            theTapRepository.getDSTheTap().collect {
                if (it.isSuccessful) {
                    _theTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSTheTapTheoKH(maKH: String) {
        viewModelScope.launch {
            theTapRepository.getDSTheTapTheoKH(maKH).collect {
                if (it.isSuccessful) {
                    _theTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getTheTap(maThe: String) {
        viewModelScope.launch {
            theTapRepository.getTheTap(maThe).collect {
                if (it.isSuccessful) {
                    _theTap.value = it.body()
                }
            }
        }
    }

    fun insertTheTap(theTapModel: TheTapModel) {
        viewModelScope.launch {
            theTapRepository.insertTheTap(theTapModel).collect {
                if (it.isSuccessful) {
                    _theTap.value = it.body()
                }
            }
        }
    }

    fun updateTheTap(theTapModel: TheTapModel) {
        viewModelScope.launch {
            theTapRepository.updateTheTap(theTapModel).collect {
                if (it.isSuccessful) {
                    _theTap.value = it.body()
                }
            }
        }
    }

    fun deleteTheTap(maThe: String) {
        viewModelScope.launch {
            theTapRepository.deleteTheTap(maThe)
            getDSTheTap()
        }
    }

    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //----------------------------------Bài tập------------------------
    //Live data
    private val _baiTaps = MutableStateFlow(emptyList<BaiTapModel>())
    val baiTaps: StateFlow<List<BaiTapModel>> = _baiTaps
    private val _baiTap: MutableStateFlow<BaiTapModel?> = MutableStateFlow(null)
    val baiTap: StateFlow<BaiTapModel?> = _baiTap

    fun getBaiTap(idBT: Int) {
        viewModelScope.launch {
            baiTapRepository.getBaiTap(idBT).collect {
                if (it.isSuccessful) {
                    _baiTap.value = it.body()
                }
            }
        }
    }

    fun getDSBaiTap() {
        viewModelScope.launch {
            baiTapRepository.getDSBaiTap().collect {
                if (it.isSuccessful) {
                    _baiTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun insertBaiTap(baiTapModel: BaiTapModel) {
        viewModelScope.launch {
            baiTapRepository.insertBaiTap(baiTapModel).collect {
                if (it.isSuccessful) {
                    _baiTap.value = it.body()
                }
                getDSBaiTap()
            }
        }
    }

    fun updateBaiTap(baiTapModel: BaiTapModel) {
        viewModelScope.launch {
            baiTapRepository.updateBaiTap(baiTapModel).collect {
                if (it.isSuccessful) {
                    _baiTap.value = it.body()
                }
                getDSBaiTap()
            }
        }
    }

    fun deleteBaiTap(idBT: Int) {
        viewModelScope.launch {
            baiTapRepository.deleteBaiTap(idBT)
            getDSBaiTap()
        }
    }

    //-------------------------------------------------------------------------
    //---------------------------------- Khuyến mại -----------------------------
    //Live data
    private val _khuyenMais = MutableStateFlow(emptyList<KhuyenMaiModel>())
    val khuyenMais: StateFlow<List<KhuyenMaiModel>> = _khuyenMais
    private val _khuyenMai: MutableStateFlow<KhuyenMaiModel?> = MutableStateFlow(null)
    val khuyenMai: StateFlow<KhuyenMaiModel?> = _khuyenMai

    fun getDSKhuyenMai() {
        viewModelScope.launch {
            khuyenMaiRepository.getDSKhuyenMai().collect {
                if (it.isSuccessful) {
                    _khuyenMais.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSKhuyenMaiTheoNV(maNV: String) {
        viewModelScope.launch {
            khuyenMaiRepository.getDSKhuyenMaiTheoNV(maNV).collect {
                if (it.isSuccessful) {
                    _khuyenMais.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getKhuyenMai(idKM: Int) {
        viewModelScope.launch {
            khuyenMaiRepository.getKhuyenMai(idKM).collect {
                if (it.isSuccessful) {
                    _khuyenMai.value = it.body()
                }
            }
        }
    }

    fun insertKhuyenMai(khuyenMaiModel: KhuyenMaiModel) {
        viewModelScope.launch {
            khuyenMaiRepository.insertKhuyenMai(khuyenMaiModel).collect {
                if (it.isSuccessful) {
                    _khuyenMai.value = it.body()
                }
            }
        }
    }

    fun updateKhuyenMai(khuyenMaiModel: KhuyenMaiModel) {
        viewModelScope.launch {
            khuyenMaiRepository.updateKhuyenMai(khuyenMaiModel).collect {
                if (it.isSuccessful) {
                    _khuyenMai.value = it.body()
                }
            }
        }
    }

    fun deleteKhuyenMai(idKM: Int) {
        viewModelScope.launch {
            khuyenMaiRepository.deleteKhuyenMai(idKM)
            getDSKhuyenMai()
        }
    }
    //---------------------------------------------------------------------------
    //------------------------------CT khuyến mại---------------------------------
    //Live data
    private val _ctKhuyenMais = MutableStateFlow(emptyList<CtKhuyenMaiModel>())
    val ctKhuyenMais: StateFlow<List<CtKhuyenMaiModel>> = _ctKhuyenMais
    private val _ctKhuyenMai: MutableStateFlow<CtKhuyenMaiModel?> = MutableStateFlow(null)
    val ctKhuyenMai: StateFlow<CtKhuyenMaiModel?> = _ctKhuyenMai

    fun getDSCtKhuyenMai() {
        viewModelScope.launch {
            ctKhuyenMaiRepository.getDSCtKhuyenMai().collect {
                if (it.isSuccessful) {
                    _ctKhuyenMais.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSCtKhuyenMaiTheoGT(maGT: String) {
        viewModelScope.launch {
            ctKhuyenMaiRepository.getDSCtKhuyenMaiTheoGT(maGT).collect {
                if (it.isSuccessful) {
                    _ctKhuyenMais.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSCtKhuyenMaiTheoKM(idKM: Int) {
        viewModelScope.launch {
            ctKhuyenMaiRepository.getDSCtKhuyenMaiTheoKM(idKM).collect {
                // it.body()?.orEmpty()
                it.body().orEmpty()
                if (it.isSuccessful) {
                    _ctKhuyenMais.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getCtKhuyenMai(idCtKhuyenMai: Int) {
        viewModelScope.launch {
            ctKhuyenMaiRepository.getCtKhuyenMai(idCtKhuyenMai).collect {
                if (it.isSuccessful) {
                    _ctKhuyenMai.value = it.body()
                }
            }
        }
    }

    fun insertCtKhuyenMai(ctKhuyenMaiModel: CtKhuyenMaiModel) {
        viewModelScope.launch {
            ctKhuyenMaiRepository.insertCtKhuyenMai(ctKhuyenMaiModel).collect {
                if (it.isSuccessful) {
                    _ctKhuyenMai.value = it.body()
                }
                getDSCtKhuyenMai()
            }
        }
    }

    fun updateCtKhuyenMai(ctKhuyenMaiModel: CtKhuyenMaiModel) {
        viewModelScope.launch {
            ctKhuyenMaiRepository.updateCtKhuyenMai(ctKhuyenMaiModel).collect {
                if (it.isSuccessful) {
                    _ctKhuyenMai.value = it.body()
                }
                getDSCtKhuyenMai()
            }
        }
    }

    fun deleteCtKhuyenMai(idCTKM: Int) {
        viewModelScope.launch {
            ctKhuyenMaiRepository.deleteCtKhuyenMai(idCTKM)
            getDSCtKhuyenMai()
        }
    }
    //-------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    //------------------------------CT bài tập---------------------------------
    //Live data
    private val _ctBaiTaps = MutableStateFlow(emptyList<CtBaiTapModel>())
    val ctBaiTaps: StateFlow<List<CtBaiTapModel>> = _ctBaiTaps
    private val _ctBaiTap: MutableStateFlow<CtBaiTapModel?> = MutableStateFlow(null)
    val ctBaiTap: StateFlow<CtBaiTapModel?> = _ctBaiTap

    fun getDSCtBaiTap() {
        viewModelScope.launch {
            ctBaiTapRepository.getDSCtBaiTap().collect {
                if (it.isSuccessful) {
                    _ctBaiTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSCtBaiTapTheoGT(maGT: String) {
        viewModelScope.launch {
            ctBaiTapRepository.getDSCtBaiTapTheoGT(maGT).collect {
                if (it.isSuccessful) {
                    _ctBaiTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getDSCtBaiTapTheoBT(idBT: Int) {
        viewModelScope.launch {
            ctBaiTapRepository.getDSCtBaiTapTheoBT(idBT).collect {
                // it.body()?.orEmpty()
                it.body().orEmpty()
                if (it.isSuccessful) {
                    _ctBaiTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getCtBaiTap(idCtBaiTap: Int) {
        viewModelScope.launch {
            ctBaiTapRepository.getCtBaiTap(idCtBaiTap).collect {
                if (it.isSuccessful) {
                    _ctBaiTap.value = it.body()
                }
            }
        }
    }

    fun insertCtBaiTap(ctBaiTapModel: CtBaiTapModel) {
        viewModelScope.launch {
            ctBaiTapRepository.insertCtBaiTap(ctBaiTapModel).collect {
                if (it.isSuccessful) {
                    _ctBaiTap.value = it.body()
                }
                getDSCtBaiTap()
            }
        }
    }

    fun updateCtBaiTap(ctBaiTapModel: CtBaiTapModel) {
        viewModelScope.launch {
            ctBaiTapRepository.updateCtBaiTap(ctBaiTapModel).collect {
                if (it.isSuccessful) {
                    _ctBaiTap.value = it.body()
                }
                getDSCtBaiTap()
            }
        }
    }

    fun deleteCtBaiTap(idCTBT: Int) {
        viewModelScope.launch {
            ctBaiTapRepository.deleteCtBaiTap(idCTBT)
            getDSCtBaiTap()
        }
    }
    //---------------------------------------------------------------------------
    //------------------------------CT thẻ tập---------------------------------
    //Live data
    private val _ctTheTaps = MutableStateFlow(emptyList<CtTheTapModel>())
    val ctTheTaps: StateFlow<List<CtTheTapModel>> = _ctTheTaps
    private val _ctTheTap: MutableStateFlow<CtTheTapModel?> = MutableStateFlow(null)
    val ctTheTap: StateFlow<CtTheTapModel?> = _ctTheTap

    fun getDSCtTheTap() {
        viewModelScope.launch {
            ctTheTapRepository.getDSCtTheTap().collect {
                if (it.isSuccessful) {
                    _ctTheTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }
    fun getDSCtTheTapThang(ngayBD: String, ngayKT: String) {
        viewModelScope.launch {
            ctTheTapRepository.getDSCtTheTapThang(ngayBD, ngayKT).collect {
                if (it.isSuccessful) {
                    _ctTheTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }
    fun getDSCtTheTapTheoDV(ngayBD: String, ngayKT: String) {
        viewModelScope.launch {
            ctTheTapRepository.getDSCtTheTapTheoDV(ngayBD, ngayKT).collect {
                if (it.isSuccessful) {
                    _ctTheTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }
    fun getDSCtTheTapTheoGT(maGT: String) {
        viewModelScope.launch {
            ctTheTapRepository.getDSCtTheTapTheoGT(maGT).collect {
                if (it.isSuccessful) {
                    _ctTheTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getCtTheTapTheoThe(maThe: String) {
        viewModelScope.launch {
            ctTheTapRepository.getCtTheTapTheoThe(maThe).collect {
                if (it.isSuccessful) {
                    _ctTheTap.value = it.body()
                }
            }
        }
    }

    fun getDSCtTheTapTheoHD(maHD: String) {
        viewModelScope.launch {
            ctTheTapRepository.getDSCtTheTapTheoHD(maHD).collect {
                // it.body()?.orEmpty()
                it.body().orEmpty()
                if (it.isSuccessful) {
                    _ctTheTaps.value = it.body() ?: emptyList()
                }
            }
        }
    }

    fun getCtTheTap(idCtTheTap: Int) {
        viewModelScope.launch {
            ctTheTapRepository.getCtTheTap(idCtTheTap).collect {
                if (it.isSuccessful) {
                    _ctTheTap.value = it.body()
                }
            }
        }
    }

    fun insertCtTheTap(ctTheTapModel: CtTheTapModel) {
        viewModelScope.launch {
            ctTheTapRepository.insertCtTheTap(ctTheTapModel).collect {
                if (it.isSuccessful) {
                    _ctTheTap.value = it.body()
                }
                getDSCtTheTap()
            }
        }
    }

    fun updateCtTheTap(ctTheTapModel: CtTheTapModel) {
        viewModelScope.launch {
            ctTheTapRepository.updateCtTheTap(ctTheTapModel).collect {
                if (it.isSuccessful) {
                    _ctTheTap.value = it.body()
                }
                getDSCtTheTap()
            }
        }
    }

    fun deleteCtTheTap(idCTThe: Int) {
        viewModelScope.launch {
            ctTheTapRepository.deleteCtTheTap(idCTThe)
            getDSCtTheTap()
        }
    }
    //-------------------------------------------------------------------------

//==============================================PASS DATA===========================================

    //==================================================================================================

}