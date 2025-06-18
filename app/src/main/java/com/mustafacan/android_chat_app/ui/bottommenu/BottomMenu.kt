package com.mustafacan.android_chat_app.ui.bottommenu

import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.navigation.getBottomBarItemColors
import com.mustafacan.core.ui.theme.BackgroundDark

@Composable
fun BottomMenu(
    navController: NavController
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

            NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.hasRoute(item::class) } == true,
                label = { Text(text = stringResource(id = item.titleResource), maxLines = 1) },
                onClick = {
                    navController.navigate(item) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, icon = {
                    Icon(painter = painterResource(id = item.icon!!),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) ) },
                alwaysShowLabel = true,
                colors = getBottomBarItemColors()
            )

        }
    }


}