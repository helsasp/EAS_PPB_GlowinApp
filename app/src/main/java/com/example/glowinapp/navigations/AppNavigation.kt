package com.example.glowinapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.glowinapp.screens.*
import com.example.glowinapp.model.Product

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController)
        }

        composable("home") {
            MainScreen(navController)
        }

        composable("membership") {
            MembershipScreen(navController)
        }

        composable("payment") {
            // You'll need to pass cart and total amount here
            // For now, using dummy data
            PaymentScreen(
                navController = navController,
                cart = emptyList(), // Pass actual cart data
                totalAmount = 0.0 // Pass actual total
            )
        }
    }
}