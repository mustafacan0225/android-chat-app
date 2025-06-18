package com.mustafacan.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryLight = Color(0xFF004F6C)
val SecondaryLight = Color(0xFFCCC2DC)
val TertiaryLight = Color(0xFF006C94)
val BackgroundLight = Color(0xFFFFFFFF)
val SurfaceLight = Color(0xFFFCFBFB)
val OnPrimaryLight = Color(0xFF808080)
val OnSecondaryLight = Color(0xFF808080)
val OnTertiaryLight = Color(0xFF808080)
val OnBackgroundLight = Color(0xFF808080)
val OnSurfaceLight = Color(0xFF808080)


val PrimaryDark = Color(0xFF63CCF3)
val SecondaryDark = Color(0xFFCCC2DC)
val TertiaryDark = Color(0xFF006C94)
val BackgroundDark = Color(0xFF004F6C)
val SurfaceDark = Color(0xFF0285B4)
val OnPrimaryDark = Color(0xFFF8F5F5)
val OnSecondaryDark = Color(0xFFF8F5F5)
val OnTertiaryDark = Color(0xFFF8F5F5)
val OnBackgroundDark = Color(0xFFF8F5F5)
val OnSurfaceDark = Color(0xFFF8F5F5)


val CardItemBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF006C94) else Color.White

val CardItemTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.7f) else Color(0xFF1C1B1F)

val CardButtonTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.7f) else Color.White

val TitleTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.7f) else Color(0xFF004F6C)

val ShimmerEffectColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) BackgroundDark else Color.LightGray

