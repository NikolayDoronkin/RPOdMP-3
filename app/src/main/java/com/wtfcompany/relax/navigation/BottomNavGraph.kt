package com.wtfcompany.relax.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wtfcompany.relax.view.home.HomeScreen
import com.wtfcompany.relax.view.listen.ListenScreen
import com.wtfcompany.relax.view.main.BottomBarScreen
import com.wtfcompany.relax.view.profile.ProfileScreen

@Composable
fun BottomNavGraph(bottomNavController: NavHostController, navController: NavHostController) {
    NavHost(navController = bottomNavController, startDestination = BottomBarScreen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Listen.route) {
            ListenScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}