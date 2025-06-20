package com.example.glowinapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.glowinapp.R

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFDF6F0) // soft cream/pink background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Optional Hero Image (make sure you add image in res/drawable)
            Image(
                painter = painterResource(id = R.drawable.glowin_logo), // ganti dengan gambar kamu
                contentDescription = "Glowin Logo",
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to Glowin'",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B2F2F)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Where your beauty shines brighter ✨",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF6D4C41)
                )
            }

            Button(
                onClick = { navController.navigate("home") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Get Glowin'",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}
