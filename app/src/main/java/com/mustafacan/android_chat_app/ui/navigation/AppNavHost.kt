package com.mustafacan.android_chat_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mustafacan.android_chat_app.ui.home.HomeRoute
import com.mustafacan.android_chat_app.ui.home.HomeViewModel
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
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeRoute(viewModel)
        }
    }
}