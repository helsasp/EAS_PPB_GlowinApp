package com.example.glowinapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.glowinapp.R

data class Product(
    val name: String,
    val desc: String,
    val price: String = "",
    val imageRes: Int = 0
)

@Composable
fun HomeScreen() {
    val bestProducts = listOf(
        Product("Rare Beauty Soft Pinch Blush", "Natural flush, buildable", "$23", R.drawable.product1),
        Product("Charlotte Flawless Filter", "Glow booster for skin", "$44", R.drawable.product2),
        Product("Sephora Cream Lip Stain", "Long-lasting matte", "$15", R.drawable.product3),
        Product("NARS Radiant Concealer", "High coverage & glow", "$32", R.drawable.product4),
        Product("Laneige Lip Sleeping Mask", "Hydrating overnight", "$24", R.drawable.product5),
        Product("Fenty Gloss Bomb", "Universal lip luminizer", "$21", R.drawable.product6),
        Product("Glow Recipe Watermelon Dew", "Hydrating serum", "$39", R.drawable.product7),
        Product("Benefit Hoola Bronzer", "Natural matte finish", "$35", R.drawable.product8),
        Product("Tarte Shape Tape", "Iconic full coverage", "$31", R.drawable.product9),
        Product("Hourglass Vanish Stick", "Seamless skin stick", "$49", R.drawable.product10)
    )

    val wishlist = remember {
        mutableStateListOf(
            Product("Dior Addict Lip Glow", "Color-reviving balm with shine"),
            Product("Kylie Matte Lip Kit", "Iconic long-lasting matte lips"),
            Product("Sol de Janeiro Bum Bum Cream", "Fast-absorbing body cream"),
            Product("Rare Beauty Lip Oil", "Glossy lip tint with shine"),
            Product("Urban Decay Setting Spray", "Locks makeup all day")
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFDF6F0)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Good Morning, Helsa", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF3B2F2F))
                Text("Le's et Glowin'", fontSize = 16.sp, color = Color(0xFFE91E63))
            }

            item { SectionTitle("Best Products") }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(bestProducts) { product ->
                        BestProductCard(product)
                    }
                }
            }

            item { SectionTitle("Your Glowin' Wishlist") }

            items(wishlist, key = { it.name }) { item ->
                FavoriteCard(
                    product = item,
                    onToggleLove = {
                        wishlist.remove(item) // ❤️ klik → langsung ilang
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Color(0xFFE0E0E0))
                Spacer(modifier = Modifier.height(12.dp))
                FooterInfo()
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF5D4037),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun BestProductCard(product: Product) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(300.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            if (product.imageRes != 0) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(product.name, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, maxLines = 2)
            Text(product.desc, fontSize = 12.sp, color = Color.Gray, maxLines = 2)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.price,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE91E63)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                repeat(5) {
                    Text("⭐", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun FavoriteCard(product: Product, onToggleLove: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(product.desc, fontSize = 13.sp, color = Color.Gray)
            }
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Unlove",
                tint = Color(0xFFE91E63),
                modifier = Modifier.clickable { onToggleLove() }
            )
        }
    }
}

@Composable
fun FooterInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .width(120.dp)
        )

        Text(
            text = "Glowin’ - Your Beauty Companion",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF3B2F2F)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("📍 1253 Melrose Ave, Los Angeles, CA 90046", fontSize = 14.sp, color = Color.Gray)
        Text("✉️ customersupport@glowin.com", fontSize = 14.sp, color = Color.Gray)
        Text("📞 +1 (213) 555-0192", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "© 2025 Glowin’ Beauty Inc. All rights reserved.",
            fontSize = 12.sp,
            color = Color.LightGray
        )
    }
}
