package com.mustafacan.core.ui.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mustafacan.core.ui.theme.OnSurfaceDark
import com.mustafacan.core.ui.theme.OnSurfaceLight
import com.mustafacan.core.ui.theme.PrimaryDark
import com.mustafacan.core.ui.theme.PrimaryLight
import com.mustafacan.core.ui.theme.SecondaryLight

@Composable
fun getBottomBarItemColors(): NavigationBarItemColors {
    return if (isSystemInDarkTheme()) {
        NavigationBarItemDefaults.colors(
            selectedIconColor = OnSurfaceDark,
            selectedTextColor = OnSurfaceDark,
            indicatorColor = PrimaryDark,
            unselectedIconColor = Color.LightGray,
            unselectedTextColor = Color.LightGray
        )
    } else {
        NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color.White,
            indicatorColor = PrimaryDark,
            unselectedIconColor = Color.LightGray,
            unselectedTextColor = Color.LightGray
        )
    }
}