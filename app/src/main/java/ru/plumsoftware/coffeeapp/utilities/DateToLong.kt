package ru.plumsoftware.coffeeapp.utilities

import android.annotation.SuppressLint
import java.lang.reflect.InvocationTargetException
import java.text.SimpleDateFormat
import java.util.Calendar

@SuppressLint("SimpleDateFormat")
fun dateToLong(inputString: String): Long? {
    return try {
        val dateFormat = SimpleDateFormat("ddMMyyyy")
        val date = dateFormat.parse(inputString)
        val c = Calendar.getInstance()
        c.time = date!!
        c.timeInMillis
    } catch (_: Exception) {
        0
    }
}