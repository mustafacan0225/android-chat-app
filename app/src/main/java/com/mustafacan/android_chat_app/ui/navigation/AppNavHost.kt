package com.mustafacan.android_chat_app.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mustafacan.auth.ui.navigation.AuthNavHost
import com.mustafacan.core.ui.navigation.NavDestinationItem

@Composable
fun AppNavHost(navController: NavHostController) {
    Log.d("ComposeTrace", "AppNavHost recomposed")
    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Login
    ) {
        composable<NavDestinationItem.Login> {
            //login/register
            AuthNavHost(parentNavController = navController)
        }
    }
}