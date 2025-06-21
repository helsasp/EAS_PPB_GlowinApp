// Enhanced MainScreen.kt with REAL product images
package com.example.glowinapp.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import com.example.glowinapp.model.Product
import com.example.glowinapp.R

// Helper function to check if image resource is valid
@Composable
fun isValidImageResource(imageRes: Int): Boolean {
    val context = LocalContext.current
    return try {
        context.resources.getDrawable(imageRes, null)
        true
    } catch (e: Exception) {
        false
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val tabs = listOf("Home", "Discover", "Cart", "Profile")
    var selectedTab by remember { mutableStateOf(0) }

    // Wishlist shared state
    val wishlist = remember { mutableStateListOf<Product>() }

    // Cart shared state
    val cart = remember { mutableStateListOf<Product>() }

    // State to trigger recomposition when cart changes
    var cartVersion by remember { mutableStateOf(0) }

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
        cartVersion++ // Trigger recomposition
    }

    fun increaseQuantity(product: Product) {
        val index = cart.indexOf(product)
        if (index != -1) {
            cart[index] = product.copy(quantity = product.quantity + 1)
            cartVersion++ // Trigger recomposition
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
            cartVersion++ // Trigger recomposition
        }
    }

    // Calculate total with reactive state
    val totalAmount by remember {
        derivedStateOf {
            cart.sumOf { product ->
                val priceString = product.price
                    .replace("$", "")
                    .replace(",", "")
                    .trim()

                val price = try {
                    priceString.toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                }

                price * product.quantity
            }
        }
    }

    Scaffold(
        bottomBar = {
            EnhancedBottomNavigation(
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                cartItemCount = cart.sumOf { it.quantity }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeScreen(
                    wishlist = wishlist,
                    onToggleWishlist = { toggleWishlist(it) }
                )
                1 -> DiscoverScreen(
                    wishlist = wishlist,
                    onToggleWishlist = { toggleWishlist(it) },
                    onAddToCart = { addToCart(it) }
                )
                2 -> EnhancedCartScreen(
                    cart = cart,
                    onIncreaseQuantity = { increaseQuantity(it) },
                    onDecreaseQuantity = { decreaseQuantity(it) },
                    onContinueToPayment = {
                        navController.navigate("payment")
                    },
                    totalAmount = totalAmount,
                    cartVersion = cartVersion
                )
                3 -> ProfileScreen(navController)
            }
        }
    }
}

@Composable
fun EnhancedBottomNavigation(
    tabs: List<String>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    cartItemCount: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            tabs.forEachIndexed { index, label ->
                NavigationBarItem(
                    icon = {
                        Box {
                            when (label) {
                                "Home" -> Icon(
                                    Icons.Default.Home,
                                    contentDescription = null,
                                    tint = if (selectedTab == index) Color(0xFFE91E63) else Color.Gray
                                )
                                "Discover" -> Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    tint = if (selectedTab == index) Color(0xFFE91E63) else Color.Gray
                                )
                                "Cart" -> {
                                    Icon(
                                        Icons.Default.ShoppingCart,
                                        contentDescription = null,
                                        tint = if (selectedTab == index) Color(0xFFE91E63) else Color.Gray
                                    )
                                    if (cartItemCount > 0) {
                                        Badge(
                                            modifier = Modifier.offset(x = 12.dp, y = (-12).dp),
                                            containerColor = Color(0xFFE91E63)
                                        ) {
                                            Text(
                                                text = cartItemCount.toString(),
                                                color = Color.White,
                                                fontSize = 10.sp
                                            )
                                        }
                                    }
                                }
                                "Profile" -> Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = if (selectedTab == index) Color(0xFFE91E63) else Color.Gray
                                )
                            }
                        }
                    },
                    label = {
                        Text(
                            label,
                            color = if (selectedTab == index) Color(0xFFE91E63) else Color.Gray,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    selected = selectedTab == index,
                    onClick = { onTabSelected(index) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFE91E63),
                        selectedTextColor = Color(0xFFE91E63),
                        indicatorColor = Color(0xFFE91E63).copy(alpha = 0.1f)
                    )
                )
            }
        }
    }
}

// Enhanced CartScreen with REAL product images
@Composable
fun EnhancedCartScreen(
    cart: List<Product>,
    onIncreaseQuantity: (Product) -> Unit,
    onDecreaseQuantity: (Product) -> Unit,
    onContinueToPayment: () -> Unit,
    totalAmount: Double,
    cartVersion: Int
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFDF6F0),
            Color(0xFFFFFFFF)
        )
    )

    if (cart.isEmpty()) {
        EmptyCartView()
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradientBrush)
                    .padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Your Cart",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3B2F2F)
                    )
                    Text(
                        "${cart.sumOf { it.quantity }} items",
                        fontSize = 16.sp,
                        color = Color(0xFFE91E63),
                        fontWeight = FontWeight.Medium
                    )
                }

                // Cart Items
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cart, key = { "${it.name}-${it.quantity}-$cartVersion" }) { product ->
                        EnhancedCartItemWithImage(
                            product = product,
                            onIncreaseQuantity = onIncreaseQuantity,
                            onDecreaseQuantity = onDecreaseQuantity
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Order Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Order Summary",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3B2F2F),
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        OrderSummaryRow(
                            "Subtotal",
                            "$${String.format("%.2f", totalAmount)}"
                        )
                        OrderSummaryRow(
                            "Gold Discount (15%)",
                            "-$${String.format("%.2f", totalAmount * 0.15)}",
                            Color(0xFFE91E63)
                        )
                        OrderSummaryRow(
                            "Shipping",
                            "FREE",
                            Color(0xFFE91E63)
                        )

                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = Color.LightGray
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total:",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3B2F2F)
                            )
                            Text(
                                text = "$${String.format("%.2f", totalAmount * 0.85)}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE91E63)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Continue to Payment Button
                Button(
                    onClick = onContinueToPayment,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_payment),
                            contentDescription = "Payment",
                            tint = Color.White
                        )
                        Text(
                            "Continue to Payment - $${String.format("%.2f", totalAmount * 0.85)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderSummaryRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF3B2F2F)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = if (valueColor == Color(0xFFE91E63)) FontWeight.Bold else FontWeight.Medium,
            color = valueColor
        )
    }
}

// NEW: Enhanced Cart Item with REAL images
@Composable
fun EnhancedCartItemWithImage(
    product: Product,
    onIncreaseQuantity: (Product) -> Unit,
    onDecreaseQuantity: (Product) -> Unit
) {
    // Calculate item total
    val priceString = product.price.replace("$", "").replace(",", "").trim()
    val itemPrice = try {
        priceString.toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }
    val itemTotal = itemPrice * product.quantity

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ENHANCED: Product Image with fallback (FIXED - No try-catch around Composable)
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                // Check if we have a valid image resource
                val hasValidImage = product.imageRes != 0 && isValidImageResource(product.imageRes)

                if (hasValidImage) {
                    // Load actual image resource
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Beautiful placeholder when no image or invalid image
                    BeautifulImagePlaceholder(product.name)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF3B2F2F)
                )
                Text(
                    product.brand,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Unit: ${product.price}",
                    fontSize = 14.sp,
                    color = Color(0xFFE91E63),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "Total: $${String.format("%.2f", itemTotal)}",
                    fontSize = 12.sp,
                    color = Color(0xFF3B2F2F),
                    fontWeight = FontWeight.Bold
                )
            }

            // Quantity Controls
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F8F8)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(4.dp)
                ) {
                    IconButton(
                        onClick = { onDecreaseQuantity(product) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_remove),
                            contentDescription = "Decrease",
                            tint = Color(0xFFE91E63)
                        )
                    }
                    Text(
                        product.quantity.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    IconButton(
                        onClick = { onIncreaseQuantity(product) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = Color(0xFFE91E63)
                        )
                    }
                }
            }
        }
    }
}

// NEW: Beautiful Image Placeholder
@Composable
fun BeautifulImagePlaceholder(productName: String) {
    val emoji = when {
        productName.contains("Lip", ignoreCase = true) -> "💄"
        productName.contains("Skin", ignoreCase = true) -> "🧴"
        productName.contains("Face", ignoreCase = true) -> "✨"
        productName.contains("Eye", ignoreCase = true) -> "👁️"
        productName.contains("Blush", ignoreCase = true) -> "🌸"
        productName.contains("Foundation", ignoreCase = true) -> "🎨"
        productName.contains("Concealer", ignoreCase = true) -> "🔸"
        productName.contains("Mascara", ignoreCase = true) -> "👁️‍🗨️"
        productName.contains("Moisturizer", ignoreCase = true) -> "💧"
        productName.contains("Serum", ignoreCase = true) -> "✨"
        productName.contains("Cream", ignoreCase = true) -> "🥛"
        productName.contains("Oil", ignoreCase = true) -> "🫒"
        productName.contains("Mask", ignoreCase = true) -> "😴"
        productName.contains("Tint", ignoreCase = true) -> "🌺"
        productName.contains("Gloss", ignoreCase = true) -> "✨"
        productName.contains("Kit", ignoreCase = true) -> "🎁"
        else -> "💅"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFE91E63).copy(alpha = 0.1f),
                        Color(0xFFF8BBD9).copy(alpha = 0.2f),
                        Color(0xFFFDF6F0)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = 32.sp
        )
    }
}

@Composable
fun EmptyCartView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("🛒", fontSize = 64.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Your cart is empty",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3B2F2F)
            )
            Text(
                "Add some beautiful products to get started!",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}