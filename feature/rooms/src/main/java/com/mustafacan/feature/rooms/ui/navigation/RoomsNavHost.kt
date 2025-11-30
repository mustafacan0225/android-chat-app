package com.mustafacan.feature.rooms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.core.ui.animation.transition.Transition
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.feature.rooms.ui.rooms.RoomsRoute
import com.mustafacan.feature.rooms.ui.rooms.RoomsViewModel

@Composable
fun RoomsNavHost(parentNavController: NavHostController) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.ChatRooms
    ) {


        composable<NavDestinationItem.ChatRooms>(
            enterTransition = { Transition.enterFromLeft() },
            exitTransition = { Transition.exitToLeft() },
            popEnterTransition = { Transition.enterFromLeft() },
            popExitTransition = { Transition.exitToLeft() }) {
            val viewModel = hiltViewModel<RoomsViewModel>()
            RoomsRoute(viewModel, navController, parentNavController)
        }

    }
}