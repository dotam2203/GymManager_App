package com.gym

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

 /*fun replaceString(s: String): String {
     var sToiUu = s
     sToiUu = sToiUu.trim()
     val arrWord = sToiUu.split(" ");
     sToiUu = ""
     for (word in arrWord) {
         var newWord = word.lowercase(Locale.getDefault())
         if (newWord.length > 0) {
             newWord = newWord.replaceFirst((newWord[0] + ""), (newWord[0] + "").uppercase(Locale.getDefault()))
             sToiUu += newWord + " "
         }
     }
     return sToiUu.trim()
 }
 fun randomMaNV(s: String, ma: String): String{
     var sRandom = replaceString(s)
     var str = ""
     val wordArr = sRandom.split(" ")
     for(word in wordArr){
         if(word.length > 0){
             str += word[0].toString()
         }
     }
     var _str: Int = ma.substring(2).toInt()
     if(_str < 10){
         str = str.plus("0")
         str = str.plus(_str + 1)
     }
     else if(_str >= 10){
         str = str.plus(_str + 1)
     }
     return str.trim()
 }
fun currentDate(): String{
    val sdf = SimpleDateFormat("dd/M/yyyy")
    val currentDate = sdf.format(Date())
    return currentDate.toString().trim()
}*/
fun main() {
    //println("maNV: ${randomMaNV("gym", "G11")}")
    /*val str: String = "1234567"
    val numberFormat = NumberFormat.getCurrencyInstance()
    numberFormat.maximumFractionDigits = 0;
    val convert = numberFormat.format(str.toInt())

    println(convert.substring(1))*/

    //println("date: ${currentDate()}")
    /*val date = "02/03/2022"
    val d: List<Any> = date.split("/")
    var year: String? = ""
    var month: String? = ""
    var day: String? = ""
    day = d[0].toString().trim()
    month = d[1].toString().trim()
    year = d[2].toString().trim()
    val dateFormat = "$year/$month/$day"
    println("date format: $dateFormat")*/
    /*val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    val formatted = current.format(formatter)
    println("Current Date and Time is: $formatted")*/
    val sRandom = "Nhân Viên Thích Hát"
    var str = ""
    val wordArr = sRandom.split(" ")
    for(word in wordArr){
        if(word.isNotEmpty()){
            str += word[0].toString()
        }
    }
    println("abc: $str")
}
