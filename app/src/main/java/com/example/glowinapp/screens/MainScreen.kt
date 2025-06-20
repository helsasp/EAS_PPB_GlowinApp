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

    // Wishlist shared state
    val wishlist = remember { mutableStateListOf<Product>() }

    // Cart shared state
    val cart = remember { mutableStateListOf<Product>() }

    fun toggleWishlist(product: Product) {
        if (wishlist.contains(product)) {
            wishlist.remove(product)
        } else {
            wishlist.add(product)
        }
    }

    fun addToCart(product: Product) {
        val existing = cart.find { it.name == product.name }
        if (existing != null) {
            val index = cart.indexOf(existing)
            cart[index] = existing.copy(quantity = existing.quantity + 1)
        } else {
            cart.add(product.copy(quantity = 1))
        }
    }

    fun increaseQuantity(product: Product) {
        val index = cart.indexOf(product)
        if (index != -1) {
            cart[index] = product.copy(quantity = product.quantity + 1)
        }
    }

    fun decreaseQuantity(product: Product) {
        val index = cart.indexOf(product)
        if (index != -1) {
            if (product.quantity > 1) {
                cart[index] = product.copy(quantity = product.quantity - 1)
            } else {
                cart.removeAt(index)
            }
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
                1 -> DiscoverScreen(
                    wishlist = wishlist,
                    onToggleWishlist = { toggleWishlist(it) },
                    onAddToCart = { addToCart(it) }
                )
                2 -> CartScreen(
                    cart = cart,
                    onIncreaseQuantity = { increaseQuantity(it) },
                    onDecreaseQuantity = { decreaseQuantity(it) }
                )
                3 -> ProfileScreen()
            }
        }
    }
}
