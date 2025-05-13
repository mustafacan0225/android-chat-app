package com.mustafacan.core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavDestinationItem(var titleResource: Int, var icon: Int? = null) {

    @Serializable
    object Login

    @Serializable
    object Register

}