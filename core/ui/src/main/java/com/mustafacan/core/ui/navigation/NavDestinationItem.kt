package com.mustafacan.core.ui.navigation

import com.mustafacan.core.ui.R
import kotlinx.serialization.Serializable

@Serializable
sealed class NavDestinationItem(var titleResource: Int, var icon: Int? = null) {

    @Serializable
    object Splash

    @Serializable
    object Login

    @Serializable
    object Register

    @Serializable
    object Home

    @Serializable
    object ChatRooms: NavDestinationItem(R.string.bottom_menu_chatroom, R.drawable.bottom_menu_chatroom)

    @Serializable
    object Users: NavDestinationItem(R.string.bottom_menu_users, R.drawable.bottom_menu_users)

    @Serializable
    object OnlineUsers

    @Serializable
    object AllUsers

    @Serializable
    object Messages: NavDestinationItem(R.string.bottom_menu_messages, R.drawable.bottom_menu_messages)

    @Serializable
    object Support: NavDestinationItem(R.string.bottom_menu_support, R.drawable.bottom_menu_support)





}