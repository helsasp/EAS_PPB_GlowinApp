package com.example.glowinapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.glowinapp.model.Product

@Composable
fun MainScreen() {
    val tabs = listOf("Home", "Discover", "Cart", "Profile")
    var selectedTab by remember { mutableStateOf(0) }

    // Wishlist state shared between screens
    val wishlist = remember { mutableStateListOf<Product>() }

    fun toggleWishlist(product: Product) {
        if (wishlist.contains(product)) {
            wishlist.remove(product)
        } else {
            wishlist.add(product)
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, label ->
                    NavigationBarItem(
                        icon = {
                            when (label) {
                                "Home" -> Icon(Icons.Default.Home, contentDescription = null)
                                "Discover" -> Icon(Icons.Default.Search, contentDescription = null)
                                "Cart" -> Icon(Icons.Default.ShoppingCart, contentDescription = null)
                                "Profile" -> Icon(Icons.Default.Person, contentDescription = null)
                            }
                        },
                        label = { Text(label) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeScreen(wishlist = wishlist, onToggleWishlist = { toggleWishlist(it) })
                1 -> DiscoverScreen(wishlist = wishlist, onToggleWishlist = { toggleWishlist(it) })
                2 -> CartScreen()
                3 -> ProfileScreen()
            }
        }
    }
}
