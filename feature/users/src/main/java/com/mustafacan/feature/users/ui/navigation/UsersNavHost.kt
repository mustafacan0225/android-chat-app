package com.mustafacan.feature.users.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.core.ui.animation.transition.Transition
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.feature.users.ui.allusers.AllUsersRoute
import com.mustafacan.feature.users.ui.allusers.AllUsersViewModel
import com.mustafacan.feature.users.ui.home.HomeRoute
import com.mustafacan.feature.users.ui.home.HomeViewModel
import com.mustafacan.feature.users.ui.onlineusers.OnlineUsersRoute
import com.mustafacan.feature.users.ui.onlineusers.OnlineUsersViewModel

@Composable
fun UsersNavHost(parentNavController: NavHostController) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Users
    ) {
        composable<NavDestinationItem.Users>(
            enterTransition = { Transition.enterFromLeft() },
            exitTransition = { Transition.exitToLeft() },
            popEnterTransition = { Transition.enterFromLeft() },
            popExitTransition = { Transition.exitToLeft() }) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeRoute(viewModel, navController, parentNavController)
        }

        composable<NavDestinationItem.OnlineUsers>(
            enterTransition = { Transition.enterFromRight() },
            exitTransition = { Transition.exitToRight() },
            popEnterTransition = { Transition.enterFromRight() },
            popExitTransition = { Transition.exitToRight() }) {
            val viewModel = hiltViewModel<OnlineUsersViewModel>()
            OnlineUsersRoute(viewModel, navController, parentNavController)
        }

        composable<NavDestinationItem.AllUsers>(
            enterTransition = { Transition.enterFromLeft() },
            exitTransition = { Transition.exitToLeft() },
            popEnterTransition = { Transition.enterFromLeft() },
            popExitTransition = { Transition.exitToLeft() }) {
            val viewModel = hiltViewModel<AllUsersViewModel>()
            AllUsersRoute(viewModel, navController, parentNavController)
        }

    }
}