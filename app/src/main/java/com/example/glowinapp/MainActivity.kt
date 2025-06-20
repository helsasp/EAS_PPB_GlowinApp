package com.example.glowinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.glowinapp.navigation.AppNavigation
import com.example.glowinapp.ui.theme.GlowinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlowinTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
