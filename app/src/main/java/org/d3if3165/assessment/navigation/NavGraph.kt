package org.d3if3165.assessment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if3165.assessment.ui.screen.AboutScreen
import org.d3if3165.assessment.ui.screen.MainMenu
import org.d3if3165.assessment.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainMenu.route
    ) {
        composable(route = Screen.MainMenu.route) {
            MainMenu(navController)
        }
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
    }
}