package ru.plumsoftware.coffeeapp.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.plumsoftware.coffeeapp.R

//region::Font weight
fun getRegularWeight(): FontWeight = FontWeight(weight = 400)
fun getMediumWeight(): FontWeight = FontWeight(weight = 500)
fun getBoldWeight(): FontWeight = FontWeight(weight = 700)
//endregion

//region::Font size
fun getTitleLargeFontSize() = 24.sp
fun getTitleMediumFontSize() = 20.sp
fun getTitleSmallFontSize() = 16.sp

fun getLabelMediumFontSize() = 16.sp
fun getLabelSmallFontSize() = 14.sp

fun getHeadlineLargeFontSize() = 32.sp
//endregion

//region::Font resources
fun getMainFont(): FontFamily = FontFamily(Font(R.font.sfpro_text_regular))
fun getSplashScreenTitleFont(): FontFamily = FontFamily(Font(R.font.magnolia_script))
//endregion

//region::Letter spacing
fun getTitleLargeLetterSpacing() = 0.04.sp
fun getTitleMediumLetterSpacing() = 0.04.sp
fun getTitleSmallLetterSpacing() = 0.04.sp

fun getLabelMediumLetterSpacing() = 0.04.sp
fun getLabelSmallLetterSpacing() = 0.04.sp

fun getHeadlineLargeLetterSpacing() = 0.04.sp
//endregion

//region::Line height
fun getTitleLargeLineHeight() = 20.sp
fun getTitleMediumLineHeight() = 20.sp
fun getTitleSmallLineHeight() = 20.sp

fun getLabelMediumLineHeight() = 20.sp
fun getLabelSmallLineHeight() = 20.sp

fun getHeadlineLargeLineHeight() = 20.sp
//endregion