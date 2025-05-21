package com.mustafacan.android_chat_app.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mustafacan.android_chat_app.ui.bottommenu.BottomMenu
import com.mustafacan.android_chat_app.ui.navigation.HomeNavHost

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val bottomBarVisibilityState = rememberSaveable { mutableStateOf(true) }
    Scaffold(bottomBar = {
        AnimatedVisibility(
            visible = bottomBarVisibilityState.value,
            enter = slideInVertically(animationSpec = tween(1000), initialOffsetY = { it }),
            exit = slideOutVertically(animationSpec = tween(1000), targetOffsetY = { it }),
            content = {
                BottomMenu(
                    navController = navController
                )
            }
        )
    }) {
        Box(modifier = Modifier.padding(it)) {
            HomeNavHost(navController = navController)
        }

    }
}