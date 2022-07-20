package com.gym.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.model.*
import com.gym.repository.*
import kotlinx.coroutines.launch

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  07/07/2022
 */
class ViewModel: ViewModel() {
    private val goiTapRepository = GoiTapRepository()
    private val khachHangRepository = KhachHangRepository()
    private val loaiKhRepository = LoaiKhRepository()
    private val loaiGtRepository = LoaiGtRepository()
    private val nhanVienRepository = NhanVienRepository()
    private val phanQuyenRepository = PhanQuyenRepository()
    private val taiKhoanRepository = TaiKhoanRepository()
    //------------------------------Gói tập---------------------------------
    //Live data
    private val _goiTaps = MutableLiveData<List<GoiTapModel>>()
    private val _goiTap = MutableLiveData<GoiTapModel>()
    val goiTaps: LiveData<List<GoiTapModel>?> = _goiTaps
    val goiTap: LiveData<GoiTapModel?> = _goiTap

    fun getDSGoiTap() {
        viewModelScope.launch {
            val response = goiTapRepository.getDSGoiTap()
            _goiTaps.postValue(response)
        }
    }
    fun getDSGoiTapTheoLoaiGT(idLoaiGT: Int) {
        viewModelScope.launch {
            val response = goiTapRepository.getDSGoiTapTheoLoaiGT(idLoaiGT)
            _goiTaps.postValue(response)
        }
    }
    fun getGoiTap(maGT: String) {
        viewModelScope.launch {
            val response = goiTapRepository.getGoiTap(maGT)
            _goiTap.postValue(response)
        }
    }
    fun insertGoiTap(GoiTapModel: GoiTapModel){
        viewModelScope.launch {
            val response = goiTapRepository.insertGoiTap(GoiTapModel)
            _goiTap.postValue(response)
        }
    }
    fun updateGoiTap(GoiTapModel: GoiTapModel){
        viewModelScope.launch {
            val response = goiTapRepository.updateGoiTap(GoiTapModel)
            _goiTap.postValue(response)
            //_goiTap.value = response
        }
    }
    fun deleteGoiTap(maGT: String){
        viewModelScope.launch {
            goiTapRepository.deleteGoiTap(maGT)
        }
    }
    //-------------------------------------------------------------------------
    //----------------------------------Khách hàng-----------------------------
    //Live data
    private val _khachHangs = MutableLiveData<List<KhachHangModel>>()
    private val _khachHang = MutableLiveData<KhachHangModel>()
    val khachHangs: LiveData<List<KhachHangModel>?> = _khachHangs
    val khachHang: LiveData<KhachHangModel?> = _khachHang

    fun getDSKhachHang() {
        viewModelScope.launch {
            val response = khachHangRepository.getDSKhachHang()
            _khachHangs.postValue(response)
        }
    }
    fun getDSKhachHangTheoLoaiKH(idLoaiKH: Int) {
        viewModelScope.launch {
            val response = khachHangRepository.getDSKhachHangTheoLoaiKH(idLoaiKH)
            _khachHangs.postValue(response)
        }
    }
    fun getKhachHang(maKH: String) {
        viewModelScope.launch {
            val response = khachHangRepository.getKhachHang(maKH)
            _khachHang.postValue(response)
        }
    }
    fun insertKhachHang(KhachHangModel: KhachHangModel){
        viewModelScope.launch {
            val response = khachHangRepository.insertKhachHang(KhachHangModel)
            _khachHang.postValue(response)
        }
    }
    fun updateKhachHang(KhachHangModel: KhachHangModel){
        viewModelScope.launch {
            val response = khachHangRepository.updateKhachHang(KhachHangModel)
            _khachHang.postValue(response)
            //_khachHang.value = response
        }
    }
    fun deleteKhachHang(maKH: String){
        viewModelScope.launch {
            khachHangRepository.deleteKhachHang(maKH)
        }
    }
    //-------------------------------------------------------------------------
    //----------------------------------Loại khách hàng------------------------
    //Live Data
    private val _loaiKHs = MutableLiveData<List<LoaiKhModel>>()
    private val _loaiKH = MutableLiveData<LoaiKhModel>()
    val loaiKHs: LiveData<List<LoaiKhModel>?> = _loaiKHs
    val loaiKH: LiveData<LoaiKhModel?> = _loaiKH

    fun getLoaiKH(idLoaiKH: Int){
        viewModelScope.launch {
            val response = loaiKhRepository.getLoaiKH(idLoaiKH)
            _loaiKH.postValue(response)
        }
    }
    fun getDSLoaiKH() {
        viewModelScope.launch {
            val response = loaiKhRepository.getDSLoaiKH()
            _loaiKHs.postValue(response)
        }
    }
    fun insertLoaiKH(loaiKhModel: LoaiKhModel){
        viewModelScope.launch {
            val response = loaiKhRepository.insertLoaiKH(loaiKhModel)
            _loaiKH.postValue(response)
        }
    }
    fun updateLoaiKH(loaiKhModel: LoaiKhModel){
        viewModelScope.launch {
            val response = loaiKhRepository.updateLoaiKH(loaiKhModel)
            _loaiKH.postValue(response)
        }
    }
    fun deleteLoaiKH(id: Int){
        viewModelScope.launch {
            loaiKhRepository.deleteLoaiKH(id)
        }
    }
    //-------------------------------------------------------------------------
    //----------------------------------Loại Gói Tập-----------------------------
    //Live Data
    private val _loaiGTs = MutableLiveData<List<LoaiGtModel>>()
    private val _loaiGT = MutableLiveData<LoaiGtModel>()
    val loaiGTs: LiveData<List<LoaiGtModel>?> = _loaiGTs
    val loaiGT: LiveData<LoaiGtModel?> = _loaiGT

    fun getLoaiGT(idLoaiGT: Int){
        viewModelScope.launch {
            val response = loaiGtRepository.getLoaiGT(idLoaiGT)
            _loaiGT.postValue(response)
        }
    }
    fun getDSLoaiGT() {
        viewModelScope.launch {
            val response = loaiGtRepository.getDSLoaiGT()
            _loaiGTs.postValue(response)
        }
    }
    fun insertLoaiGT(loaiGtModel: LoaiGtModel){
        viewModelScope.launch {
            val response = loaiGtRepository.insertLoaiGT(loaiGtModel)
            _loaiGT.postValue(response)
        }
    }
    fun updateLoaiGT(loaiGtModel: LoaiGtModel){
        viewModelScope.launch {
            val response = loaiGtRepository.updateLoaiGT(loaiGtModel)
            _loaiGT.postValue(response)
        }
    }
    fun deleteLoaiGT(id: Int){
        viewModelScope.launch {
            loaiGtRepository.deleteLoaiGT(id)
        }
    }
    //-------------------------------------------------------------------------
    //----------------------------------Nhân viên-----------------------------
    //Live Data
    private val _nhanViens = MutableLiveData<List<NhanVienModel>>()
    private val _nhanVien = MutableLiveData<NhanVienModel>()
    val nhanViens: LiveData<List<NhanVienModel>?> = _nhanViens
    val nhanVien: LiveData<NhanVienModel?> = _nhanVien

    fun getNhanVien(maNV: String){
        viewModelScope.launch {
            val response = nhanVienRepository.getNhanVien(maNV)
            _nhanVien.postValue(response)
        }
    }
    fun getDSNhanVien() {
        viewModelScope.launch {
            val response = nhanVienRepository.getDSNhanVien()
            _nhanViens.postValue(response)
        }
    }
    fun insertNhanVien(nhanVienModel: NhanVienModel){
        viewModelScope.launch {
            val response = nhanVienRepository.insertNhanVien(nhanVienModel)
            _nhanVien.postValue(response)
        }
    }
    fun updateNhanVien(nhanVienModel: NhanVienModel){
        viewModelScope.launch {
            val response = nhanVienRepository.updateNhanVien(nhanVienModel)
            _nhanVien.postValue(response)
        }
    }
    fun deleteNhanVien(maNV: String){
        viewModelScope.launch {
            nhanVienRepository.deleteNhanVien(maNV)
        }
    }
    //-------------------------------------------------------------------------
    //----------------------------------Phân quyền-----------------------------
    //Live Data
    private val _quyens = MutableLiveData<List<PhanQuyenModel>>()
    private val _quyen = MutableLiveData<PhanQuyenModel>()
    val quyens: LiveData<List<PhanQuyenModel>?> = _quyens
    val quyen: LiveData<PhanQuyenModel?> = _quyen

    fun getQuyen(maQuyen: String){
        viewModelScope.launch {
            val response = phanQuyenRepository.getQuyen(maQuyen)
            _quyen.postValue(response)
        }
    }
    fun getDSQuyen() {
        viewModelScope.launch {
            val response = phanQuyenRepository.getDSQuyen()
            _quyens.postValue(response)
        }
    }
    fun insertQuyen(phanQuyenModel: PhanQuyenModel){
        viewModelScope.launch {
            val response = phanQuyenRepository.insertQuyen(phanQuyenModel)
            _quyen.postValue(response)
        }
    }
    fun updateQuyen(phanQuyenModel: PhanQuyenModel){
        viewModelScope.launch {
            val response = phanQuyenRepository.updateQuyen(phanQuyenModel)
            _quyen.postValue(response)
        }
    }
    fun deleteQuyen(maNV: String){
        viewModelScope.launch {
            phanQuyenRepository.deleteQuyen(maNV)
        }
    }
    //------------------------------------------------------------------------
    //------------------------------Tài khoản---------------------------------
    //Live data
    private val _taiKhoans = MutableLiveData<List<TaiKhoanModel>>()
    private val _taiKhoan = MutableLiveData<TaiKhoanModel>()
    val taiKhoans: LiveData<List<TaiKhoanModel>?> = _taiKhoans
    val taiKhoan: LiveData<TaiKhoanModel?> = _taiKhoan

    fun getDSTaiKhoan() {
        viewModelScope.launch {
            val response = taiKhoanRepository.getDSTaiKhoan()
            _taiKhoans.postValue(response)
        }
    }
    fun getDSTaiKhoanTheoQuyen(maQuyen: String) {
        viewModelScope.launch {
            val response = taiKhoanRepository.getDSTaiKhoanTheoQuyen(maQuyen)
            _taiKhoans.postValue(response)
        }
    }
    fun getTaiKhoan(maTK: String) {
        viewModelScope.launch {
            val response = taiKhoanRepository.getTaiKhoan(maTK)
            _taiKhoan.postValue(response)
        }
    }
    fun insertTaiKhoan(TaiKhoanModel: TaiKhoanModel){
        viewModelScope.launch {
            val response = taiKhoanRepository.insertTaiKhoan(TaiKhoanModel)
            _taiKhoan.postValue(response)
        }
    }
    fun updateTaiKhoan(TaiKhoanModel: TaiKhoanModel){
        viewModelScope.launch {
            val response = taiKhoanRepository.updateTaiKhoan(TaiKhoanModel)
            _taiKhoan.postValue(response)
            //_taiKhoan.value = response
        }
    }
    fun deleteTaiKhoan(maTK: String){
        viewModelScope.launch {
            taiKhoanRepository.deleteTaiKhoan(maTK)
        }
    }
    //==============================================PASS DATA===========================================

    //==================================================================================================
}