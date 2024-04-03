package org.d3if3165.assessment.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object MainMenu: Screen("mainMenu")
}