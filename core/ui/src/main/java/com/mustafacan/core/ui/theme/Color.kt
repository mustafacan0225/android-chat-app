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

val MessagePageHeaderColor: Color
@Composable
get() = if (isSystemInDarkTheme()) BackgroundDark else Color.White

val MessagePageBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) BackgroundDark else Color.White

val ProgressColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else BackgroundDark

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

val SeperatorColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.7f) else Color(0xFF004F6C)

val ShimmerEffectColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) BackgroundDark else Color.LightGray

val MessageCardBackgroundColorForSender: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF007A9F) else Color(0xFFE1F5FE)

val MessageCardDateColorForSender: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFB0DDE9) else Color(0xFF5F6B75)

val MessageCardTextColorForSender: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFE5F6FB) else Color(0xFF0D0D0D)

val MessageCardBackgroundColorForReceiver: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF1A2B32) else Color(0xFFF1F1F1)

val MessageCardDateColorForReceiver: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF8FBFCB) else Color(0xFF7A7A7A)

val MessageCardTextColorForReceiver: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFE5F6FB) else Color(0xFF1A1A1A)

val MessageCardUserNameTextColorForReceiver: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF5EC5F8) else Color(0xFF1976D2)

