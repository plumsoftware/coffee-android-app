package ru.plumsoftware.coffeeapp.ui.theme

import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp
import racra.compose.smooth_corner_rect_library.AbsoluteSmoothCornerShape

internal val Shapes = Shapes(
    medium = AbsoluteSmoothCornerShape(cornerRadius = 40.dp, smoothnessAsPercent = 60),
    small = AbsoluteSmoothCornerShape(cornerRadius = 12.dp, smoothnessAsPercent = 60),
    extraSmall = AbsoluteSmoothCornerShape(cornerRadius = 2.dp, smoothnessAsPercent = 60)
)