package com.mustafacan.android_chat_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.auth.ui.navigation.AuthNavHost
import com.mustafacan.core.ui.navigation.NavDestinationItem

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Login
    ) {
        composable<NavDestinationItem.Login> {
            AuthNavHost(parentNavController = navController)
        }
    }
}