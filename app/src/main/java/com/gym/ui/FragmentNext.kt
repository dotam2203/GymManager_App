package com.gym.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.gym.R
import com.gym.model.*
import com.gym.ui.viewmodel.ViewModel
import kotlinx.coroutines.delay
import pl.droidsonroids.gif.GifImageView
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.collections.ArrayList

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  06/07/2022
 */
abstract class FragmentNext : Fragment() {
    val viewModel: ViewModel by lazy {
        ViewModelProvider(this)[ViewModel::class.java]
    }
    fun getFragment(view: View, id: Int) {
        Navigation.findNavController(view).navigate(id)
    }

    fun replaceFragment(id: Int, fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(id, fragment).commit();
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        return currentDate.toString().trim()
    }
    //ngày hiển thị ở local, input: yyyy/MM/dd -> output: dd/MM/yyyy
    fun getFormatDate(date: String): String {
        val d: List<Any> = date.split("/")
        var year: String? = ""
        var month: String? = ""
        var day: String? = ""
        day = d[2].toString().trim()
        month = d[1].toString().trim()
        year = d[0].toString().trim()
        return "$day/$month/$year"
    }
    //lấy ngày trên api xuống hiển thị ở local, input: yyyy-MM-dd -> output: dd/MM/yyyy
    fun getFormatDateFromAPI(date: String): String {
        val d: List<Any> = date.split("-")
        var year: String? = ""
        var month: String? = ""
        var day: String? = ""
        day = d[2].toString().trim()
        month = d[1].toString().trim()
        year = d[0].toString().trim()
        return "$day/$month/$year"
    }
    //lấy ngày trên api xuống hiển thị ở local, input: yyyy-MM-dd -> output: yyyy/MM/dd
    fun getFormatDateFromAPI1(date: String): String {
        val d: List<Any> = date.split("-")
        var year: String? = ""
        var month: String? = ""
        var day: String? = ""
        day = d[2].toString().trim()
        month = d[1].toString().trim()
        year = d[0].toString().trim()
        return "$year/$month/$day"
    }
    //ngày post lên api, input: dd/MM/yyyy -> output: yyyy-MM-dd
    fun getFormatDateToApi(date: String): String {
        val d: List<Any> = date.split("/")
        var year: String? = ""
        var month: String? = ""
        var day: String? = ""
        day = d[0].toString().trim()
        month = d[1].toString().trim()
        year = d[2].toString().trim()
        return "$year-$month-$day"
    }
    //lấy ngày nhập vào để thống kê,input: dd/MM/yyyy -> output: yyyy/MM/dd
    fun getFormatDateCompareTo(date: String): String {
        val d: List<Any> = date.split("/")
        var year: String? = ""
        var month: String? = ""
        var day: String? = ""
        day = d[0].toString().trim()
        month = d[1].toString().trim()
        year = d[2].toString().trim()
        return "$year/$month/$day"
    }

    fun formatCurrentMoney(number: Double): String {
        val localID = Locale("IND", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localID)
        val formatCurrent: String = numberFormat.format(number)
        val split: List<String> = formatCurrent.split(",")
        val length: Int = split[0].length
        return "${split[0].substring(0, 2)}.${split[0].substring(2, length)}"
    }
    fun replaceString(s: String): String {
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

    fun randomString(s: String, ma: String): String {
        val sRandom = replaceString(s)
        var str = ""
        val wordArr = sRandom.split(" ")
        for (word in wordArr) {
            if (word.isNotEmpty()) {
                str += word[0].toString()
            }
        }
        val str1: Int = ma.trim().substring(2).toInt()
        if (str1 < 9) {
            str = str.plus("0")
            str = str.plus(str1 + 1)
        }
        else {
            str = str.plus(str1 + 1)
        }
        return str.trim()
    }
    fun getDateTime(): String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        return current.format(formatter)
    }
    fun formatMoney(money: String): String {
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(money.toInt()).substring(1)
    }
    fun getSoLuongLoaiGT(tenLoaiGT: String): ArrayList<String>{
        val slDangKy = ArrayList<String>()
        var sl: String = ""
        when(tenLoaiGT){
            "Ngày" -> {
                for( i in 1..6){
                    sl = "$i ngày"
                    slDangKy.add(sl)
                }
            }
            "Tuần" -> {
                for( i in 1..3){
                    sl = "$i tuần"
                    slDangKy.add(sl)
                }
            }
            "Tháng" -> {
                for( i in 1..11){
                    sl = "$i tháng"
                    slDangKy.add(sl)
                }
            }
            "Năm" -> {
                for( i in 1..5){
                    sl = "$i năm"
                    slDangKy.add(sl)
                }
            }
            else -> arrayListOf<Any>("")

        }
        return slDangKy
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(NoSuchAlgorithmException::class, NoSuchPaddingException::class, IllegalBlockSizeException::class, BadPaddingException::class, InvalidKeyException::class)
    fun maHoaPassApi(original: String): String {
        val SECRET_KEY = "12345678"
        val skeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "DES")
        val cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        val byteEncrypted: ByteArray? = cipher.doFinal(original.toByteArray())
        return Base64.getEncoder().encodeToString(byteEncrypted)
    }
    fun compareToDate(dateStart: String): Boolean {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val currentDate = sdf.format(Date()).toString().trim()
        val date2: Date = sdf.parse(currentDate) as Date
        val date1: Date = sdf.parse(dateStart) as Date
        if(date1.compareTo(date2) == 0)
            return true
        else if(date1 < date2)
            return true
        else if(date1 > date2)
            return false
        return false
    }

    //gia = 100,000 => 100000
    fun formatMoneyToAPI(gia: String): String{
        //gia = 100,000
        val str1 = gia.split(",")
        var str = ""
        for(i in str1.indices){
            str += str1[i]
        }
        return if(str != "") str.trim()
        else "0"
    }
    //--------------------------------------------
    fun dialogPopMessage(msg: String, drawable: Int){
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.pop_message)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes : WindowManager.LayoutParams = window!!.attributes
        windowAtrributes.gravity = Gravity.CENTER
        window.attributes = windowAtrributes
        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        dialog.setCancelable(false)
        dialog.show()
        val imvGif: GifImageView = dialog.findViewById(R.id.imvGif)
        val tvPopMessage: TextView = dialog.findViewById(R.id.tvPopMessage)
        imvGif.setImageResource(drawable) //R.drawable.ic_done
        tvPopMessage.text = msg
        lifecycleScope.launchWhenCreated {
            delay(3000L)
            dialog.dismiss()
        }

    }
    fun sendMessageFromMail(email: String, title: String, message: String, text: String) {
        //pass: akyfsgnsbwqoxyta
        val toMail ="vectorgym2203@gmail.com"
        val toPass = "akyfsgnsbwqoxyta"

        val host = "smtp.gmail.com"
        val properties = System.getProperties()
        properties.apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", host)
            put("mail.smtp.port", "587")
        }
        //val session = Session
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(toMail, toPass)
            }
        })
        try {
            val mimeMessage: Message = MimeMessage(session)
            mimeMessage.apply {
                setFrom(InternetAddress(toMail))
                addRecipient(Message.RecipientType.TO, InternetAddress(email))
                subject = title
                setText(message)
            }
            sendMail().execute(mimeMessage)
            lifecycleScope.launchWhenCreated {
                delay(2000L)
                dialogPopMessage(text, R.drawable.ic_ok)
            }
            //Toast.makeText(requireContext(),text, Toast.LENGTH_LONG).show()
        }catch (e: MessagingException){
            e.printStackTrace()
            Log.e("TAG", "sendMessageFromMail: ${e.message}", )
        }
    }
    fun createPassword(): String {
        val generator = Random()
        val value: Int = generator.nextInt((999999 - 100000) + 1) + 100000
        return "$value"
    }
    class sendMail : AsyncTask<Message, String, String>() {
        override fun doInBackground(vararg params: Message?): String {
            return try {
                Transport.send(params[0])
                "Success!"
            }catch (e: MessagingException){
                e.printStackTrace()
                "Fail!"
            }
        }
    }
    //=======================Call api lấy data================
    //-------------Nhân Viên--------------------------------
    fun getNhanVienByMaNV(maNV: String) : NhanVienModel{
        var nhanVien = NhanVienModel()
        viewModel.getNhanVien(maNV)
        lifecycleScope.launchWhenCreated {
            viewModel.nhanVien.collect {
                nhanVien = it ?: return@collect
            }
        }
        return nhanVien
    }
    //------------------------------------------------------
    //-------------Tài khoản - Quyền--------------------------------
    // ------------------------------------------------------
    //-------------Gói Tập-- Giá------------------------------
    fun getMaGTMax(goiTaps: ArrayList<GoiTapModel>): String {
        if (goiTaps.isNotEmpty()) {
            var max: Int = goiTaps[0].maGT.substring(2).toInt()
            var maMax = goiTaps[0].maGT
            for (i in goiTaps.indices) {
                if (max <= goiTaps[i].maGT.substring(2).toInt()) {
                    max = goiTaps[i].maGT.substring(2).toInt()
                    maMax = goiTaps[i].maGT
                }
            }
            return maMax
        }
        return "GT00"
    }
    fun getDSGoiTap(): ArrayList<GoiTapModel>{
        val goiTaps = ArrayList<GoiTapModel>()
        viewModel.getDSGoiTap()
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect{
                if(it.isNotEmpty()){
                    goiTaps.addAll(it)
                }
                else return@collect
            }
        }
        return goiTaps
    }
    fun getMoTa(maGT: String): String{
        var moTa = ""
        viewModel.getGoiTap(maGT)
        lifecycleScope.launchWhenCreated {
            viewModel.goiTap.collect{
                if(it != null)
                    moTa = it.moTa
            }
        }
        return moTa
    }
    fun getGiaGT(maGT: String): String{
        var gia = ""
        viewModel.getDSGiaTheoGoiTap(maGT)
        lifecycleScope.launchWhenCreated {
            viewModel.gias.collect{
                if(it.isNotEmpty())
                    gia = it[0].gia.trim()
            }
        }
        return gia
    }

    // ------------------------------------------------------
    //-------------Nhân viên--------------------------------
    // ------------------------------------------------------
    //-------------Khách hàng--------------------------------
    fun getMaKHMax(khachHangs: ArrayList<KhachHangModel>): String {
        if (khachHangs.isNotEmpty()) {
            var max: Int = khachHangs[0].maKH.substring(2).toInt()
            var maMax = khachHangs[0].maKH
            for (i in khachHangs.indices) {
                if (max <= khachHangs[i].maKH.substring(2).toInt()) {
                    max = khachHangs[i].maKH.substring(2).toInt()
                    maMax = khachHangs[i].maKH
                }
            }
            return maMax
        }
        return "KH00"
    }
    fun getDSKhachHang(): ArrayList<KhachHangModel>{
        val khachHangs = ArrayList<KhachHangModel>()
        viewModel.getDSKhachHang()
        lifecycleScope.launchWhenCreated {
            viewModel.khachHangs.collect{
                if(it.isNotEmpty()){
                    khachHangs.addAll(it)
                }
                else return@collect
            }
        }
        return khachHangs
    }
    fun getKhachHangByMaKH(maKH: String): KhachHangModel {
        var khachHang = KhachHangModel()
        viewModel.getKhachHang(maKH)
        lifecycleScope.launchWhenCreated {
            viewModel.khachHang.collect{
                if(it != null){
                    khachHang = it
                }
                else
                    return@collect
            }
        }
        return khachHang
    }
    // ------------------------------------------------------
    //-------------Thẻ tập - CT Thẻ Tập----------------------
    fun getMaTheMax(theTaps: java.util.ArrayList<TheTapModel>): String {
        if (theTaps.isNotEmpty()) {
            var max: Int = theTaps[0].maThe.substring(2).toInt()
            var maMax = theTaps[0].maThe
            for (i in theTaps.indices) {
                if (max <= theTaps[i].maThe.substring(2).toInt()) {
                    max = theTaps[i].maThe.substring(2).toInt()
                    maMax = theTaps[i].maThe
                }
            }
            return maMax
        }
        return "TT00"
    }
    fun getDSTheTap(): ArrayList<TheTapModel>{
        val theTaps =  ArrayList<TheTapModel>()
        viewModel.getDSTheTap()
        lifecycleScope.launchWhenCreated {
            viewModel.theTaps.collect {
                if (it.isNotEmpty()) {
                    theTaps.addAll(it)
                }
            }
        }
        return theTaps
    }
    fun getDSTheTheoKH(maKH: String): ArrayList<TheTapModel>{
        val thes = ArrayList<TheTapModel>()
        viewModel.getDSTheTapTheoKH(maKH)
        lifecycleScope.launchWhenCreated {
            viewModel.theTaps.collect{
                if(it.isNotEmpty()){
                    thes.addAll(it)
                }
                else return@collect
            }
        }
        return thes
    }
    fun getCtTheTheoTheTap(maThe: String) : CtTheTapModel{
        var ctThe = CtTheTapModel()
        viewModel.getCtTheTapTheoThe(maThe)
        lifecycleScope.launchWhenCreated {
            viewModel.ctTheTap.collect{
                if(it != null){
                    ctThe = it
                }
                else return@collect
            }
        }
        return ctThe
    }
    // ------------------------------------------------------
    //-------------Nhân viên--------------------------------
    fun getMaNVMax(nhanViens: ArrayList<NhanVienModel>): String {
        if (nhanViens.isNotEmpty()) {
            var max: Int = nhanViens[0].maNV.substring(2).toInt()
            var maMax = nhanViens[0].maNV
            for (i in nhanViens.indices) {
                if (max <= nhanViens[i].maNV.substring(2).toInt()) {
                    max = nhanViens[i].maNV.substring(2).toInt()
                    maMax = nhanViens[i].maNV
                }
            }
            return maMax
        }
        return "NV00"
    }
    fun getTenNV(maNV: String): String{
        var nv = ""
        viewModel.getNhanVien(maNV)
        lifecycleScope.launchWhenCreated {
            viewModel.nhanVien.collect{
                if(it != null)
                    nv = it.hoTen
            }
        }
        return nv
    }
    // ------------------------------------------------------

    //-------------Loại gói tập - Loại khách hàng------------
    fun getIDByTenLoaiGT(ten: String): Int {
        var id: Int = 0
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect {
                for (i in it.indices) {
                    if (ten.trim() == it[i].tenLoaiGT.trim()) {
                        id = it[i].idLoaiGT
                        break
                    }
                }
            }
        }
        return id
    }
    fun getListTenLoaiGT(): ArrayList<String> {
        val tenLoaiGTs: ArrayList<String> = arrayListOf()
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect { response ->
                if (response.isNotEmpty()) {
                    response.forEach {
                        if (it.trangThai == "Hoạt động") {
                            tenLoaiGTs.add(it.tenLoaiGT)
                        }
                    }
                } else return@collect
            }
        }
        return tenLoaiGTs
    }
    fun getLoaiGTByID(idLoaiGT: Int): LoaiGtModel {
        var loaiGT = LoaiGtModel()
        viewModel.getLoaiGT(idLoaiGT)
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGT.collect{
                if(it != null){
                    loaiGT = it
                }
                else
                    return@collect
            }
        }
        return loaiGT
    }
    fun getLoaiKHByID(idLoaiKH: Int): LoaiKhModel {
        var loaiKH = LoaiKhModel()
        viewModel.getLoaiKH(idLoaiKH)
        lifecycleScope.launchWhenCreated {
            viewModel.loaiKH.collect{
                if(it != null){
                    loaiKH = it
                }
                else
                    return@collect
            }
        }
        return loaiKH
    }
    // ------------------------------------------------------

    // -------------Thống kê--------------------------------
    //don gia = 100,000 đ => 100000
    fun tongDoanhThu(donGia: String): Long{
        //don gia = 100,000 đ
        val str1 = donGia.split(" ")
        val str2 = str1[0].split(",")
        var str = ""
        for(i in str2.indices){
            str += str2[i]
        }
        return if(str != "") str.trim().toLong()
        else 0
    }
    // ------------------------------------------------------
    //-------------Hóa đơn-------------------------------
    fun getRandomMaHD(): String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        return "HD${current.format(formatter)}"
    }
    //------------------------------------------------------

    //------------------------------------------------------
}