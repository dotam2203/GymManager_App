package com.gym.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.gym.R
import com.gym.databinding.FragmentDangkyBinding
import com.gym.firebase.NotificationHelper
import com.gym.model.*
import com.gym.ui.FragmentNext
import com.gym.ui.SingletonAccount
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.delay
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class DangKyFragment : FragmentNext(),PaymentResultListener {
    private lateinit var binding: FragmentDangkyBinding
    var khachHang = KhachHangModel()
    val loaiGTs = ArrayList<LoaiGtModel>()
    val goiTaps = ArrayList<GoiTapModel>()
    var theTaps = ArrayList<TheTapModel>()
    val gias = ArrayList<GiaGtModel>()
    var price = ArrayList<GiaGtModel>()
    var dateFormat = SimpleDateFormat(("dd/MM/yyyy"), Locale.ENGLISH)
    var changeDate: Calendar = Calendar.getInstance()
    val maNV = SingletonAccount.taiKhoan?.maNV
    var nhanVien = NhanVienModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDangkyBinding.inflate(layoutInflater)
        if(maNV != null){
            viewModel.getNhanVien(maNV)
            lifecycleScope.launchWhenCreated {
                viewModel.nhanVien.collect{
                    nhanVien = it?: return@collect
                }
            }
        }
        lifecycleScope
        binding.apply {
            btnDangKy.visibility = View.GONE
            txtNgayBD.setText(getCurrentDate())
            txtNgayKT.setText(getCurrentDate())
        }
        getTheTap(theTaps)
        reviceDataKH()
        getInitCalendar()
        getEnvent()
        return binding.root
    }

    private fun reviceDataKH() {
        parentFragmentManager.setFragmentResultListener("passData", this, object : FragmentResultListener {
            override fun onFragmentResult(requestKey: String, result: Bundle) {
                // khachHang = result.getParcelable("dataKH")?: return
                khachHang = result.getParcelable("dataKH") ?: return
                //===========================================================
                binding.btnDangKy.visibility = View.VISIBLE
                //===========================================================
            }
        })
    }

    private fun getTheTap(theTaps: ArrayList<TheTapModel>) {
        viewModel.getDSTheTap()
        lifecycleScope.launchWhenCreated {
            viewModel.theTaps.collect {
                if (it.isNotEmpty()) {
                    theTaps.addAll(it)
                }
            }
        }
    }

    private fun getInitViewModel() {
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect {
                if (it.isNotEmpty()) {
                    loaiGTs.addAll(it)
                }
            }
        }
        viewModel.getDSGoiTap()
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect {
                if (it.isNotEmpty()) {
                    goiTaps.addAll(it)
                }
            }
        }
        viewModel.getDSGia()
        lifecycleScope.launchWhenCreated {
            viewModel.gias.collect {
                if (it.isNotEmpty()) {
                    gias.addAll(it)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getEnvent() {
        var selectLoaiGT: String = ""
        var selectGT: String = ""
        var selectSL: String = ""
        var idLoaiGT: Int = 0
        var maGT: String = ""
        var goiTap = GoiTapModel()
        var loaiGT = LoaiGtModel()
        var count: Int = 0
        binding.apply {
            val tenLoaiGTs: ArrayList<String> = getListTenLoaiGT()
            Log.e("Error", "loaiGT array: ${getListTenLoaiGT().size} ")
            val arrAdapterLoaiGT = ArrayAdapter(requireContext(), R.layout.dropdown_item, tenLoaiGTs)
            spLoaiGT.setAdapter(arrAdapterLoaiGT)
            spLoaiGT.setOnItemClickListener { parent, view, position, id ->
                spGoiTap.setText("")
                spCTLoaiGT.setText("")
                selectLoaiGT = parent.getItemAtPosition(position).toString()
                //===============================================================
                idLoaiGT = getIDByTenLoaiGT(selectLoaiGT)
                //Log.e("Message", "idLoaiGT:$idLoaiGT")
                viewModel.getLoaiGT(idLoaiGT)
                lifecycleScope.launchWhenCreated {
                    viewModel.loaiGT.collect{
                        loaiGT = it?: return@collect
                    }
                }
                Toast.makeText(requireContext(), "Loại gt: ${loaiGT.idLoaiGT}", Toast.LENGTH_SHORT).show()
                //--------------------------Gói tập------------------------------
                val tenGTs: ArrayList<String> = getListTenGTByIDLoaiGT(getIDByTenLoaiGT(selectLoaiGT))
                val arrAdapterGT = ArrayAdapter(requireContext(), R.layout.dropdown_item, tenGTs)
                spGoiTap.setAdapter(arrAdapterGT)
                spGoiTap.setOnItemClickListener { parent, view, position, id ->
                    spCTLoaiGT.setText("")
                    txtGia.text = ""
                    selectGT = parent.getItemAtPosition(position).toString()

                    //-------------------------------lấy giá loại gói tập--------------------------------
                    /*idLoaiGT = getIDByTenLoaiGT(selectLoaiGT)
                    Log.e("Message11", "idLoaiGT:$idLoaiGT")*/

                    val goiTapss = getGoiTapByIDLoaiGT(idLoaiGT)
                    for (i in goiTapss.indices) {
                        if (selectGT == goiTapss[i].tenGT) {
                            maGT = goiTapss[i].maGT
                            tvMoTa.setText(goiTapss[i].moTa)
                            break
                        }
                    }
                    Log.e("Message1", "maGT:$maGT")
                    //goiTap = getGoiTapByMaGT(maGT)
                    //====================
                    viewModel.getGoiTap(maGT)
                    lifecycleScope.launchWhenCreated {
                        viewModel.goiTap.collect{
                            goiTap = it?: return@collect
                        }
                    }
                    //====================
                    Toast.makeText(requireContext(), "Loại gt: ${goiTap.maGT}", Toast.LENGTH_SHORT).show()
                    price = getGiaByMaGT(maGT)
                    Toast.makeText(requireContext(), "price ${getGiaByMaGT(maGT).size}", Toast.LENGTH_SHORT).show()
                    //Log.e("Message2", "giá:${price[0].gia}")
                    if (price.size != 0) {
                        txtGia.text = formatMoney(price[0].gia.trim())
                    }
                    //------------------------------------------------------
                }
                //-------------------------Số lượng đăng ký-----------------------
                val soLuongs: ArrayList<String> = getSoLuongLoaiGT(selectLoaiGT)
                val arrAdapterSL = ArrayAdapter(requireContext(), R.layout.dropdown_item, soLuongs)
                spCTLoaiGT.setAdapter(arrAdapterSL)
                spCTLoaiGT.setOnItemClickListener { parent, view, position, id ->
                    selectSL = parent.getItemAtPosition(position).toString()
                    changeDate = Calendar.getInstance()
                    val time = selectSL.split(" ")
                    count = time[0].trim().toInt()
                    //------------------------------
                    //Toast.makeText(requireContext(), "date change: ${changeDate.toString().trim()}", Toast.LENGTH_SHORT).show()
                    //------------------------------
                    when (selectLoaiGT) {
                        "Ngày" -> {
                            if (count != 0) {
                                changeDate.add(Calendar.DAY_OF_MONTH, count)
                            }
                        }
                        "Tuần" -> {
                            if (count != 0) {
                                changeDate.add(Calendar.WEEK_OF_MONTH, count)
                            }
                        }
                        "Tháng" -> {
                            if (count != 0) {
                                changeDate.add(Calendar.MONTH, count)
                            }
                        }
                        "Năm" -> {
                            if (count != 0) {
                                changeDate.add(Calendar.YEAR, count)
                            }
                        }
                    }
                    /*when (selectLoaiGT) {
                        "Ngày" -> {
                            if (count != null) {
                                changeDate.add(changeDate.get(Calendar.DAY_OF_MONTH),count)
                            }
                        }
                        "Tuần" -> {
                            if (count != null) {
                                changeDate.add(changeDate.get(Calendar.WEEK_OF_MONTH),count)
                            }
                        }
                        "Tháng" -> {
                            if (count != null) {
                                changeDate.add(changeDate.get(Calendar.MONTH),count)
                            }
                        }
                        "Năm" -> {
                            if (count != null) {
                                changeDate.add(changeDate.get(Calendar.YEAR),count)
                            }
                        }
                    }*/
                    txtNgayKT.setText(dateFormat.format(changeDate.time))

                    //-------------------------------Tổng tiền--------------------
                    if (price.size != 0) {
                        var tong: Int = 0
                        if (count != 0)
                            tong = (price[0].gia.trim().toInt()) * count
                        txtGia.text = formatMoney(tong.toString().trim())
                        //txtGia.text = formatCurrentMoney(price[0].gia.trim().toDouble())
                    }
                }

                //---------------------------------------------------------------
            }
            //=================================================
            btnDangKy.setOnClickListener {
                var saveTheTap = TheTapModel()
                if(compareToDate(getFormatDateApi(txtNgayBD.text.toString()))){
                    saveTheTap = TheTapModel(randomString("thẻ tập",getMaTheMax(theTaps)),getFormatDateApi(getCurrentDate()),getFormatDateApi(txtNgayBD.text.toString()),getFormatDateApi(txtNgayKT.text.toString()),"Hoạt động",khachHang.maKH)
                }
                else if(!compareToDate(getFormatDateApi(txtNgayBD.text.toString()))){
                    saveTheTap = TheTapModel(randomString("thẻ tập",getMaTheMax(theTaps)),getFormatDateApi(getCurrentDate()),getFormatDateApi(txtNgayBD.text.toString()),getFormatDateApi(txtNgayKT.text.toString()),"Khóa",khachHang.maKH)
                }
                val saveHoaDon = HoaDonModel(getRandomMaHD(),getFormatDateApi(getCurrentDate()),maNV.toString(),randomString("thẻ tập", getMaTheMax(theTaps)))
                //=======================================
                dialogYN(saveTheTap, saveHoaDon, goiTap, loaiGT, selectSL)
                //=======================================
                //viewModel.insertTheTap(TheTapModel(randomString("thẻ tập",getMaTheMax(theTaps)),getFormatDateApi(getCurrentDate()),getFormatDateApi(txtNgayBD.text.toString()),getFormatDateApi(txtNgayKT.text.toString()),"Hoạt động",khachHang.maKH))
                //Toast.makeText(requireContext(), "Lưu thẻ tập thành công!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getGoiTapByIDLoaiGT(id: Int): ArrayList<GoiTapModel> {
        val goiTaps = ArrayList<GoiTapModel>()
        viewModel.getDSGoiTapTheoLoaiGT(id)
        lifecycleScope.launchWhenCreated {
            viewModel.goiTaps.collect {
                if (it.isNotEmpty())
                    goiTaps.addAll(it)
                else return@collect
            }
        }
        return goiTaps
    }

    private fun getGiaByMaGT(maGT: String): ArrayList<GiaGtModel> {
        val price = ArrayList<GiaGtModel>()
        viewModel.getDSGiaTheoGoiTap(maGT)
        lifecycleScope.launchWhenCreated {
            viewModel.gias.collect {
                if (it.isNotEmpty()) {
                    price.addAll(it)
                } else
                    return@collect
            }
        }
        return price
    }

    private fun getInitCalendar() {
        binding.apply {
            imbCalendar.setOnClickListener {
                val datePickerFragment = DatePickerFagment()
                val support = requireActivity().supportFragmentManager
                //receive date
                support.setFragmentResultListener("REQUEST_KEY", viewLifecycleOwner) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("PASS_DATE")
                        txtNgayBD.setText(date)
                    }
                }
                //show dialog
                datePickerFragment.show(support, "DatePickerFragment")
            }
        }
    }

    //================================================
    //--------------------Loại gt----------------------
    fun getListTenLoaiGT(): ArrayList<String> {
        val tenLoaiGTs: ArrayList<String> = arrayListOf()
        viewModel.getDSLoaiGT()
        lifecycleScope.launchWhenCreated {
            viewModel.loaiGTs.collect {
                for (i in it.indices) {
                    tenLoaiGTs.add(it[i].tenLoaiGT)
                }
            }
        }
        return tenLoaiGTs
    }

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

    //--------------------------------------------
    //-------------------Gói Tập------------------
    fun getListTenGTByIDLoaiGT(id: Int): ArrayList<String> {
        val tenGTs = ArrayList<String>()
        lifecycleScope.launchWhenCreated {
            viewModel.getDSGoiTapTheoLoaiGT(id)
            viewModel.goiTaps.collect {
                Log.e("Error", "check tenGTs: ${it.size}")
                if (it.isNotEmpty()) {
                    for (i in it.indices) {
                        binding.spGoiTap.isEnabled = true
                        tenGTs.add(it[i].tenGT)
                    }
                } else if (it.isEmpty()) {
                    binding.spGoiTap.isEnabled = false
                    return@collect
                }
            }
        }
        return tenGTs
    }

    fun getMaTheMax(theTaps: ArrayList<TheTapModel>): String {
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

    //============================================
    fun dialogYN(theTapModel: TheTapModel, hoaDonModel: HoaDonModel, goiTapModel: GoiTapModel,loaiGtModel: LoaiGtModel, select: String) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_message)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes: WindowManager.LayoutParams = window!!.attributes
        windowAtrributes.gravity = Gravity.CENTER
        window.attributes = windowAtrributes
        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        dialog.setCancelable(false)
        dialog.show()
        val title: TextView = dialog.findViewById(R.id.tvMessage)
        val btnY: Button = dialog.findViewById(R.id.btnYes)
        val btnN: Button = dialog.findViewById(R.id.btnNo)
        title.text = "Vui lòng THANH TOÁN để hoàn tất đăng ký!"
        btnY.setOnClickListener {
            dialogThanhToan(theTapModel,hoaDonModel,goiTapModel,loaiGtModel, select)
            dialog.dismiss()
        }
        btnN.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun dialogThanhToan(theTapModel: TheTapModel, hoaDonModel: HoaDonModel, goiTapModel: GoiTapModel,loaiGtModel: LoaiGtModel,select: String) {
        val dialog = Dialog(activity!!)
        var ctTTModel = CtTheTapModel()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_thanhtoan)

        val window: Window? = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAtrributes: WindowManager.LayoutParams = window!!.attributes
        windowAtrributes.gravity = Gravity.CENTER
        window.attributes = windowAtrributes
        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        dialog.setCancelable(false)
        dialog.show()
        //----------------------------------------------------
        val tvHDMess: TextView = dialog.findViewById(R.id.tvHDMessage)
        val tvKHDK: TextView = dialog.findViewById(R.id.tvKHDK)
        val tvSDT: TextView = dialog.findViewById(R.id.tvSdtDK)
        val tvNgayLap: TextView = dialog.findViewById(R.id.tvNgayLap)
        val tvNgayBD: TextView = dialog.findViewById(R.id.tvBDTap)
        val tvNgayKT: TextView = dialog.findViewById(R.id.tvKTTap)
        val tvHDSo: TextView = dialog.findViewById(R.id.tvHDSo)
        val tvNVLap: TextView = dialog.findViewById(R.id.tvNVLap)
        val tvDichVu: TextView = dialog.findViewById(R.id.tvDichVu)
        val tvLoaiDV: TextView = dialog.findViewById(R.id.tvLoaiDV)
        val tvDonGia: TextView = dialog.findViewById(R.id.tvDonGia)
        val tvSoLuong: TextView = dialog.findViewById(R.id.tvSoLuong)
        val tvKhuyenMai: TextView = dialog.findViewById(R.id.tvKhuyenMai)
        val tvGiamTien: TextView = dialog.findViewById(R.id.tvGiamTien)
        val tvThanhTien: TextView = dialog.findViewById(R.id.tvThanhTien)
        val tvNoiDung: TextView = dialog.findViewById(R.id.tvNoiDung)
        //-----------------------------------------------------
        val btnThanhToan: Button = dialog.findViewById(R.id.btnThanhToan)
        val btnThanhToanSau: Button = dialog.findViewById(R.id.btnTTSau)
        val btnHuy: Button = dialog.findViewById(R.id.btnHuyTT)
        //----------------------
        tvKHDK.text = khachHang.hoTen
        tvSDT.text = khachHang.sdt
        tvNgayLap.text = getDateTime()
        tvNgayBD.text = getFormatDate(theTapModel.ngayBD)
        tvNgayKT.text = getFormatDate(theTapModel.ngayKT)
        tvHDSo.text = hoaDonModel.maHD
        tvNVLap.text = nhanVien.hoTen
        tvDichVu.text = goiTapModel.tenGT
        tvNoiDung.text = goiTapModel.moTa
        tvLoaiDV.text = loaiGtModel.tenLoaiGT
        tvDonGia.text = "${formatMoney(price[0].gia)} đ"
        tvSoLuong.text = select.substring(0,1)
        tvKhuyenMai.text = "0%"
        tvGiamTien.text = "0"
        tvThanhTien.text = "${binding.txtGia.text} đ"
        //----------------------
        btnThanhToan.setOnClickListener {
            viewModel.insertTheTap(theTapModel)
            Log.e("STT1", "1")
            lifecycleScope.launchWhenCreated {
                delay(3000L)
                viewModel.insertHoaDon(hoaDonModel)
                Log.e("STT2", "2")
                lifecycleScope.launchWhenCreated {
                    delay(3000L)
                    ctTTModel = CtTheTapModel(0,tvThanhTien.text.toString(),goiTapModel.maGT,hoaDonModel.maHD,theTapModel.maThe)
                    viewModel.insertCtTheTap(ctTTModel)
                    Log.e("STT3", "3")
                }
            }
            passData(theTapModel,hoaDonModel,ctTTModel)
            //-------------------
            NotificationHelper(requireContext(),R.drawable.ic_email,"Thanh toán dịch vụ","Khách hàng ${khachHang.hoTen} \nĐăng kí thành công dịch vụ ${goiTapModel.tenGT} \nTổng thanh toán: ${tvThanhTien.text} \nTrạng thái: Đã Thanh Toán").Notification()
            //====================
            val title = "VECTOR GYM - THANH TOÁN DỊCH VỤ THÀNH CÔNG!"
            val message = "Khách hàng: ${khachHang.hoTen}\nSố điện thoại:${khachHang.sdt} \nĐăng kí thành công dịch vụ: ${goiTapModel.tenGT}\nLoại dịch vụ: ${tvLoaiDV.text}\nNgày đăng ký: ${tvNgayLap.text} \nNgày bắt đầu: ${tvNgayBD.text}\nNgày kết thúc: ${tvNgayKT.text}\nTổng thanh toán: ${tvThanhTien.text}đ \nTrạng thái: Đã thanh toán \n\nCảm ơn quý khách hàng đã đồng hành cùng VECTOR GYM!"
            val text = "Thanh toán thành công!"
            sendMessageFromMail(khachHang.email,title,message,text)
            //====================
            dialog.setCancelable(true)
            tvHDMess.text = "Chi Tiết Hóa Đơn"
            btnThanhToanSau.visibility = View.GONE
            btnThanhToan.visibility = View.GONE
            btnHuy.visibility = View.GONE
            //====================

        }
        btnThanhToanSau.setOnClickListener {
            /*viewModel.insertTheTap(theTapModel)
            //Toast.makeText(requireContext(), "Trả sau thành công!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            dialogPopMessage("Vui lòng thanh toán trong thời gian sớm nhất!",R.drawable.ic_warning)//-------------------
            NotificationHelper(requireContext(),R.drawable.ic_email,"Thanh toán dịch vụ","Khách hàng ${khachHang.hoTen} \nĐăng kí dịch vụ ${goiTapModel.tenGT} \nTổng thanh toán: ${tvThanhTien.text}đ \nTrạng thái: Thanh toán sau").Notification()
            //====================*/
            //====================
            val title = "VECTOR GYM - CHỜ THANH TOÁN DỊCH VỤ!"
            val message = "Khách hàng: ${khachHang.hoTen}\nSố điện thoại:${khachHang.sdt} \nĐăng kí thành công dịch vụ: ${goiTapModel.tenGT}\nLoại dịch vụ: ${tvLoaiDV.text}\nNgày đăng ký: ${tvNgayLap.text} \nNgày bắt đầu: ${tvNgayBD.text}\nNgày kết thúc: ${tvNgayKT.text}\nTổng thanh toán: ${tvThanhTien.text}đ \nTrạng thái: Chưa thanh toán \nVUI LÒNG THANH TOÁN TRƯỚC NGÀY ${tvNgayBD.text}"
            val text = "Thanh toán sau!"
            sendMessageFromMail(khachHang.email,title,message,text)
            //startPayment()
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun passData(theTap: TheTapModel, hoaDon: HoaDonModel, ctTT: CtTheTapModel) {

    }
    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val checkout = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","50000")//pass amount in currency subunits
            options.put("prefill.email","gaurav.kumar@example.com")
            options.put("prefill.contact","9876543210")
            checkout.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(requireActivity(), "Payment failed ${e.message}!", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }


    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(requireActivity(), "Payment success!", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(requireActivity(), "Payment failed!", Toast.LENGTH_SHORT).show()
    }
}