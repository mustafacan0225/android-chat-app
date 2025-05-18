package com.mustafacan.android_chat_app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mustafacan.android_chat_app.ui.splash.SplashRoute
import com.mustafacan.android_chat_app.ui.splash.SplashViewModel
import com.mustafacan.core.ui.animation.transition.Transition
import com.mustafacan.feature.auth.ui.navigation.AuthNavHost
import com.mustafacan.core.ui.navigation.NavDestinationItem

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Splash
    ) {
        composable<NavDestinationItem.Splash>(
            enterTransition = { Transition.enterFromLeft() },
            exitTransition = { Transition.exitToLeft() },
            popEnterTransition = { Transition.enterFromLeft() },
            popExitTransition = { Transition.exitToLeft() }) {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashRoute(viewModel, parentNavController =  navController)
        }

        composable<NavDestinationItem.Login>{
            //login/register
            AuthNavHost(parentNavController = navController)
        }

        composable<NavDestinationItem.Home>(
            enterTransition = { Transition.enterFromRight() },
            exitTransition = { Transition.exitToRight() },
            popEnterTransition = { Transition.enterFromRight() },
            popExitTransition = { Transition.exitToRight() }) {
            Text("Home Page", Modifier.padding(16.dp, 40.dp))
        }
    }
}