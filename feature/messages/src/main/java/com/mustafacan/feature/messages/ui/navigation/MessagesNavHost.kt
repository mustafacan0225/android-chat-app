package com.mustafacan.feature.messages.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.core.ui.animation.transition.Transition
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.feature.messages.ui.messages.MessagesRoute
import com.mustafacan.feature.messages.ui.messages.MessagesViewModel

@Composable
fun MessagesNavHost(parentNavController: NavHostController) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Messages
    ) {


        composable<NavDestinationItem.Messages>(
            enterTransition = { Transition.enterFromLeft() },
            exitTransition = { Transition.exitToLeft() },
            popEnterTransition = { Transition.enterFromLeft() },
            popExitTransition = { Transition.exitToLeft() }) {
            val viewModel = hiltViewModel<MessagesViewModel>()
            MessagesRoute(viewModel, navController, parentNavController)
        }

    }
}