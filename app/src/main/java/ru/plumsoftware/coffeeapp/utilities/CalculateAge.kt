package ru.plumsoftware.coffeeapp.utilities

import java.util.Calendar

fun calculateAge(dob: Long): String {
    return if (dob == 1L) "0"
    else
        try {
            val cal1 = Calendar.getInstance()
            cal1.timeInMillis = dob

            val cal2 = Calendar.getInstance()
            cal2.timeInMillis = System.currentTimeMillis()

            val diffInMillis = cal2.timeInMillis - cal1.timeInMillis
            val diffInYears = (diffInMillis / (1000 * 60 * 60 * 24 * 365.25)).toInt()

            diffInYears.toString()
        } catch (_: Exception) {
            "0"
        }
}