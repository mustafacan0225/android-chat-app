package com.mustafacan.auth.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.auth.ui.login.LoginRoute
import com.mustafacan.auth.ui.login.LoginViewModel
import com.mustafacan.core.ui.navigation.NavDestinationItem

@Composable
fun AuthNavHost(parentNavController: NavHostController) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Login
    ) {
        composable<NavDestinationItem.Login> {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginRoute(viewModel, navController)
        }

        composable<NavDestinationItem.Register> {
            Text("Register Page", modifier = Modifier.padding(16.dp,50.dp,16.dp))
        }

    }
}