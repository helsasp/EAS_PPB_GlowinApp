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
            // For now, we'll use sample data since we can't pass complex objects through navigation
            // In a real app, you'd use a shared ViewModel or state management
            val sampleCart = listOf(
                Product("Tinted Moisturizer", "Natural skin tint with SPF", "$48", 0, 1, "", "", "Laura Mercier")
            )
            val sampleTotal = 48.0

            PaymentScreen(
                navController = navController,
                cart = sampleCart,
                totalAmount = sampleTotal
            )
        }
    }
}