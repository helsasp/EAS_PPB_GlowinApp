package com.example.glowinapp.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.glowinapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController? = null) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE91E63),
            Color(0xFFF8BBD9),
            Color(0xFFFDF6F0)
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { ProfileHeader() }
        item { StatsCard() }
        item { MembershipCard(navController) }
        item { SettingsSection() }
        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
fun ProfileHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture with Glow Effect
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFE91E63).copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            ) {
                // Simple avatar placeholder since we don't have the image
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFE91E63),
                                    Color(0xFFAD1457)
                                )
                            )
                        )
                        .border(4.dp, Color(0xFFE91E63), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👤",
                        fontSize = 40.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Helsa Ramadhani ✨",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3B2F2F)
            )

            Text(
                text = "Beauty Enthusiast",
                fontSize = 16.sp,
                color = Color(0xFFE91E63),
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Jakarta, Indonesia 🇮🇩",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun StatsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem("12", "Products Bought", R.drawable.ic_shopping_bag)
            StatItem("5", "Reviews", Icons.Default.Star)
            StatItem("8", "Wishlist", Icons.Default.Favorite)
        }
    }
}

@Composable
fun StatItem(number: String, label: String, icon: Any) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (icon) {
            is ImageVector -> {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color(0xFFE91E63),
                    modifier = Modifier.size(28.dp)
                )
            }
            is Int -> {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    tint = Color(0xFFE91E63),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = number,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3B2F2F)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MembershipCard(navController: NavHostController?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController?.navigate("membership") },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE91E63)
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFE91E63),
                            Color(0xFFAD1457)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "GLOWIN' GOLD ✨",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "Premium Member",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "2,450 points available",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "View Membership",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun SettingsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Settings & More",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3B2F2F),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SettingItem("Edit Profile", Icons.Default.Edit, {})
            SettingItem("Order History", R.drawable.ic_receipt, {})
            SettingItem("Beauty Quiz", R.drawable.ic_question_answer, {})
            SettingItem("Notifications", Icons.Default.Notifications, {})
            SettingItem("Help & Support", R.drawable.ic_help_outline, {})
            SettingItem("Privacy Policy", Icons.Default.Lock, {})

            Divider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.LightGray
            )

            SettingItem(
                "Sign Out",
                Icons.Default.ExitToApp,
                {},
                textColor = Color.Red
            )
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    icon: Any,
    onClick: () -> Unit,
    textColor: Color = Color(0xFF3B2F2F)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (icon) {
            is ImageVector -> {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (textColor == Color.Red) Color.Red else Color(0xFFE91E63),
                    modifier = Modifier.size(24.dp)
                )
            }
            is Int -> {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    tint = if (textColor == Color.Red) Color.Red else Color(0xFFE91E63),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Arrow",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}