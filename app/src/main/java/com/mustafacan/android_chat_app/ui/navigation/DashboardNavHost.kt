package com.mustafacan.android_chat_app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mustafacan.android_chat_app.ui.support.SupportScreen
import com.mustafacan.core.ui.animation.transition.Transition
import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.navigation.parcelableNavType
import com.mustafacan.feature.chat.ui.directmessage.DirectMessageRoute
import com.mustafacan.feature.chat.ui.directmessage.DirectMessageViewModel
import com.mustafacan.feature.chat.ui.groupmessage.GroupMessageRoute
import com.mustafacan.feature.chat.ui.groupmessage.GroupMessageViewModel
import com.mustafacan.feature.messages.ui.navigation.MessagesNavHost
import com.mustafacan.feature.rooms.ui.navigation.RoomsNavHost
import com.mustafacan.feature.users.ui.navigation.UsersNavHost
import kotlin.reflect.typeOf

@Composable
fun DashboardNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Users
    ) {
        composable<NavDestinationItem.Messages>(
            enterTransition = { Transition.enterFromLeft() },
            exitTransition = { Transition.exitToLeft() },
            popEnterTransition = { Transition.enterFromLeft() },
            popExitTransition = { Transition.exitToLeft() }) {
            MessagesNavHost(navController)
        }

        composable<NavDestinationItem.ChatRooms>(
            enterTransition = { Transition.enterFromLeft() },
            exitTransition = { Transition.exitToLeft() },
            popEnterTransition = { Transition.enterFromLeft() },
            popExitTransition = { Transition.exitToLeft() }){
            RoomsNavHost(navController)
        }

        composable<NavDestinationItem.Users>(
            enterTransition = { Transition.enterFromRight() },
            exitTransition = { Transition.exitToRight() },
            popEnterTransition = { Transition.enterFromRight() },
            popExitTransition = { Transition.exitToRight() }) {
            UsersNavHost(parentNavController = navController)
        }

        composable<NavDestinationItem.Support>(
            enterTransition = { Transition.enterFromRight() },
            exitTransition = { Transition.exitToRight() },
            popEnterTransition = { Transition.enterFromRight() },
            popExitTransition = { Transition.exitToRight() }) {
            SupportScreen()
        }

        composable<NavDestinationItem.DirectMessage>(typeMap = mapOf(typeOf<UserUiModel>() to NavType.parcelableNavType<UserUiModel>(), typeOf<String>() to NavType.StringType)) {
            val viewModel = hiltViewModel<DirectMessageViewModel>()
            DirectMessageRoute(viewModel, navController)
        }

        composable<NavDestinationItem.GroupMessage>(typeMap = mapOf(typeOf<UserUiModel>() to NavType.parcelableNavType<UserUiModel>(), typeOf<String>() to NavType.StringType)) {
            val viewModel = hiltViewModel<GroupMessageViewModel>()
            GroupMessageRoute(viewModel, navController)
        }
    }
}

