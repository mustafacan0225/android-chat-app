package com.mustafacan.android_chat_app.ui.bottommenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mustafacan.android_chat_app.ui.dashboard.DashboardUiEvent
import com.mustafacan.android_chat_app.ui.dashboard.DashboardUiState
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.navigation.getBottomBarItemColors
import com.mustafacan.core.ui.theme.BackgroundDark

@Composable
fun BottomMenu(
    navController: NavController,
    uiState: DashboardUiState,
    onEvent: (DashboardUiEvent) -> Unit
) {
    val items = listOf(
        NavDestinationItem.ChatRooms,
        NavDestinationItem.Messages,
        NavDestinationItem.Users,
        NavDestinationItem.Support)

    BottomAppBar(
        containerColor = BackgroundDark,
        //cutoutShape = RoundedCornerShape(50)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEachIndexed { index, item ->
            val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item::class) } == true
            NavigationBarItem(selected = isSelected,
                label = { Text(text = stringResource(id = item.titleResource), maxLines = 1) },
                onClick = {
                    if (item == NavDestinationItem.Messages)
                        onEvent(DashboardUiEvent.SetBadgeVisibilityForMessagesTab(false))
                    else if (item == NavDestinationItem.ChatRooms)
                        onEvent(DashboardUiEvent.SetBadgeVisibilityForRoomsTab(false))
                    navController.navigate(item) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, icon = {
                    Box(modifier = Modifier.size(24.dp)) {
                        Icon(
                            painter = painterResource(id = item.icon!!),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )

                        if ((item == NavDestinationItem.Messages && uiState.badgeVisibilityForMessagesTab && !isSelected)
                            || (item == NavDestinationItem.ChatRooms && uiState.badgeVisibilityForRoomsTab && !isSelected)) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .align(Alignment.TopEnd)
                                    .background(
                                        color = Color.Red,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                },
                alwaysShowLabel = true,
                colors = getBottomBarItemColors()
            )

        }
    }


}