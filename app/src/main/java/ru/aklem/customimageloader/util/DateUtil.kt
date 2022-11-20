package ru.aklem.customimageloader.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

interface DateUtil {

    fun getDate(date: String): String
    fun getTime(date: String): String
    fun getMilliseconds(orderTime: String): String

    class Base : DateUtil {
        override fun getDate(date: String): String {
            return try {
                SimpleDateFormat(DATE_FROMAT, Locale.getDefault()).format(date.toLong())
            } catch (e: Exception) {
                Log.e("DateUtil", "date parsing error $e")
                ""
            }
        }

        override fun getTime(date: String): String {
            return try {
                SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(date.toLong())
            } catch (e: Exception) {
                Log.e("DateUtil", "time parsing error $e")
                ""
            }
        }

        override fun getMilliseconds(orderTime: String): String {
            return try {
                val date = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.getDefault())
                    .parse(orderTime) ?: throw java.lang.IllegalStateException()
                date.time.toString()
            } catch (e: Exception) {
                Log.e("DateUtil", "order time parsing error $e")
                ""
            }
        }

    }

    private companion object {
        const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
        const val DATE_FROMAT = "dd MMMM yyyy"
        const val TIME_FORMAT = "HH:mm"
    }

}