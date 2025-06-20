package com.example.glowinapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.glowinapp.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("home") { MainScreen() }
    }
}
