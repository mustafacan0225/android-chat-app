package com.mustafacan.feature.auth.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.core.ui.animation.transition.Transition
import com.mustafacan.feature.auth.ui.login.LoginRoute
import com.mustafacan.feature.auth.ui.login.LoginViewModel
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.feature.auth.ui.register.RegisterRoute
import com.mustafacan.feature.auth.ui.register.RegisterViewModel

@Composable
fun AuthNavHost(parentNavController: NavHostController) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Login
    ) {
        composable<NavDestinationItem.Login>(
            enterTransition = { Transition.enterFromLeft() },
            exitTransition = { Transition.exitToLeft() },
            popEnterTransition = { Transition.enterFromLeft() },
            popExitTransition = { Transition.exitToLeft() }) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginRoute(viewModel, navController)
        }

        composable<NavDestinationItem.Register>(
            enterTransition = { Transition.enterFromRight() },
            exitTransition = { Transition.exitToRight() },
            popEnterTransition = { Transition.enterFromRight() },
            popExitTransition = { Transition.exitToRight() }) {
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterRoute(viewModel, navController)
        }

    }
}