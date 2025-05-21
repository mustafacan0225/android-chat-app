package com.mustafacan.android_chat_app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mustafacan.core.ui.animation.transition.Transition
import com.mustafacan.core.ui.navigation.NavDestinationItem

@Composable
fun HomeNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Messages
    ) {
        composable<NavDestinationItem.Messages>(
            enterTransition = { Transition.enterFromLeft() },
            exitTransition = { Transition.exitToLeft() },
            popEnterTransition = { Transition.enterFromLeft() },
            popExitTransition = { Transition.exitToLeft() }) {
            Text("Messages Page", Modifier.padding(16.dp, 40.dp))
        }

        composable<NavDestinationItem.ChatRooms>(
            enterTransition = { Transition.enterFromLeft() },
            exitTransition = { Transition.exitToLeft() },
            popEnterTransition = { Transition.enterFromLeft() },
            popExitTransition = { Transition.exitToLeft() }){
            Text("ChatRooms Page", Modifier.padding(16.dp, 40.dp))
        }

        composable<NavDestinationItem.Users>(
            enterTransition = { Transition.enterFromRight() },
            exitTransition = { Transition.exitToRight() },
            popEnterTransition = { Transition.enterFromRight() },
            popExitTransition = { Transition.exitToRight() }) {
            Text("Users Page", Modifier.padding(16.dp, 40.dp))
        }

        composable<NavDestinationItem.Support>(
            enterTransition = { Transition.enterFromRight() },
            exitTransition = { Transition.exitToRight() },
            popEnterTransition = { Transition.enterFromRight() },
            popExitTransition = { Transition.exitToRight() }) {
            Text("Support Page", Modifier.padding(16.dp, 40.dp))
        }
    }
}