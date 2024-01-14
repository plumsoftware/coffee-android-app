package ru.plumsoftware.coffeeapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle

internal val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = getMainFont(),
        fontWeight = getBoldWeight(),
        fontSize = getTitleLargeFontSize(),
        lineHeight = getTitleLargeLineHeight(),
        letterSpacing = getTitleLargeLetterSpacing()
    ),
    titleMedium = TextStyle(
        fontFamily = getMainFont(),
        fontWeight = getBoldWeight(),
        fontSize = getTitleMediumFontSize(),
        lineHeight = getTitleMediumLineHeight(),
        letterSpacing = getTitleMediumLetterSpacing()
    ),
    titleSmall = TextStyle(
        fontFamily = getMainFont(),
        fontWeight = getBoldWeight(),
        fontSize = getTitleSmallFontSize(),
        lineHeight = getTitleSmallLineHeight(),
        letterSpacing = getTitleSmallLetterSpacing()
    ),

    headlineLarge = TextStyle(
        fontFamily = getMainFont(),
        fontWeight = getMediumWeight(),
        fontSize = getHeadlineLargeFontSize(),
        lineHeight = getHeadlineLargeLineHeight(),
        letterSpacing = getHeadlineLargeLetterSpacing()
    ),

    labelMedium = TextStyle(
        fontFamily = getMainFont(),
        fontWeight = getRegularWeight(),
        fontSize = getLabelMediumFontSize(),
        lineHeight = getLabelMediumLineHeight(),
        letterSpacing = getLabelMediumLetterSpacing()
    ),
    labelSmall = TextStyle(
        fontFamily = getMainFont(),
        fontWeight = getRegularWeight(),
        fontSize = getLabelSmallFontSize(),
        lineHeight = getLabelSmallLineHeight(),
        letterSpacing = getLabelSmallLetterSpacing(),
    ),
)