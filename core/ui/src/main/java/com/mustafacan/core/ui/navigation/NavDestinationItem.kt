package com.mustafacan.core.ui.navigation

import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.model.UserUiModel
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

    @Serializable
    data class DirectMessage(
        val own: UserUiModel, val receiverUser: UserUiModel, val previousPage: String
    )

    @Serializable
    data class GroupMessage(
        val own: UserUiModel, val roomId: String, val roomName: String, val roomImage: String, val roomDescription: String, val previousPage: String
    )




}