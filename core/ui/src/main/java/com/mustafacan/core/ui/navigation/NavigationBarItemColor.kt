package com.mustafacan.core.ui.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mustafacan.core.ui.theme.OnSurfaceDark

@Composable
fun getBottomBarItemColors(): NavigationBarItemColors {
    return if (isSystemInDarkTheme()) {
        NavigationBarItemDefaults.colors(
            selectedIconColor = OnSurfaceDark,
            selectedTextColor = OnSurfaceDark,
            indicatorColor = Color(0xFF006C94),
            unselectedIconColor = Color.LightGray,
            unselectedTextColor = Color.LightGray
        )
    } else {
        NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color.White,
            indicatorColor = Color(0xFF006C94),
            unselectedIconColor = Color.LightGray,
            unselectedTextColor = Color.LightGray
        )
    }
}