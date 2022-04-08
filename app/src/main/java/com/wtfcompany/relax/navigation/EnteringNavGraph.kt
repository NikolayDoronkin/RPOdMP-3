package com.wtfcompany.relax.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wtfcompany.relax.data.entity.Photo
import com.wtfcompany.relax.view.about.AboutScreen
import com.wtfcompany.relax.view.bmi.BmiScreen
import com.wtfcompany.relax.view.editprofile.EditScreen
import com.wtfcompany.relax.view.firstenter.FirstEnterScreen
import com.wtfcompany.relax.view.guide.GuideScreen
import com.wtfcompany.relax.view.login.LoginScreen
import com.wtfcompany.relax.view.main.MainScreen
import com.wtfcompany.relax.view.menu.MenuScreen
import com.wtfcompany.relax.view.photo.PhotoScreen
import com.wtfcompany.relax.view.registration.RegistrationScreen
import com.wtfcompany.relax.view.splash.SplashScreen

@Composable
fun EnteringNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Registration.route) {
            RegistrationScreen(navController = navController)
        }
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(Screen.Photo.route) {
            val photo = navController.previousBackStackEntry?.savedStateHandle?.get<Photo>("info")
            photo?.let { PhotoScreen(navController = navController, photo = it) }
        }
        composable(Screen.Menu.route) {
            MenuScreen(navController = navController)
        }
        composable(Screen.EditProfile.route) {
            EditScreen(navController = navController)
        }
        composable(Screen.CalculateBmi.route) {
            BmiScreen(navController = navController)
        }
        composable(Screen.AboutDeveloper.route) {
            AboutScreen(navController = navController)
        }
        composable(Screen.Guide.route) {
            GuideScreen(navController = navController)
        }
        composable(Screen.FirstEnter.route) {
            FirstEnterScreen(navController = navController)
        }
    }
}
