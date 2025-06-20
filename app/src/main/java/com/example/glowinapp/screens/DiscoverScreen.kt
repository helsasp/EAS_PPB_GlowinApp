package com.example.glowinapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.glowinapp.model.Product
import com.example.glowinapp.R

@Composable
fun DiscoverScreen(
    wishlist: List<Product>,
    onToggleWishlist: (Product) -> Unit,
    onAddToCart: (Product) -> Unit // ✅ Tambahan parameter untuk cart
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val allProducts = listOf(
        Product("Lip Glow Oil", "Nourishing glossy oil", "$38", R.drawable.product1, 1, "Cherry Oil, Glycerin, Tocopherol", "Apply directly on lips for glossy shine", "Dior"),
        Product("Tinted Moisturizer", "Natural skin tint with SPF", "$48", R.drawable.product2, 1, "Titanium Dioxide, Vitamin E", "Blend with fingertips on clean skin", "Laura Mercier"),
        Product("Lip Kit", "Matte lipstick & liner", "$29", R.drawable.product3, 1, "Dimethicone, Iron Oxides", "Outline lips with liner, fill with lipstick", "Kylie Cosmetics"),
        Product("Concealer", "Radiant creamy coverage", "$32", R.drawable.product4, 1, "Water, Mica, Glycerin", "Dab under eyes and blend gently", "NARS"),
        Product("Lip Sleeping Mask", "Hydrating overnight lip mask", "$24", R.drawable.product5, 1, "Berry Mix Complex™, Shea Butter", "Apply before bed on clean lips", "Laneige"),
        Product("Watermelon Glow", "Dewy serum for glowing skin", "$39", R.drawable.product6, 1, "Watermelon Extract, Hyaluronic Acid", "Pat gently onto skin after toner", "Glow Recipe"),
        Product("Rare Beauty Blush", "Soft pinch liquid blush", "$23", R.drawable.product7, 1, "Mica, Dimethicone", "Apply 1-2 dots and blend", "Rare Beauty"),
        Product("Airbrush Flawless", "Full-coverage matte foundation", "$49", R.drawable.product8, 1, "Silica, Glycerin, Vitamin C", "Apply evenly using brush or sponge", "Charlotte Tilbury"),
        Product("Anastasia Brow Wiz", "Precise brow pencil", "$25", R.drawable.product9, 1, "Iron Oxides, Beeswax", "Fill in sparse brow areas", "Anastasia Beverly Hills"),
        Product("Bum Bum Cream", "Fast-absorbing body cream", "$48", R.drawable.product10, 1, "Guaraná, Cupuaçu Butter, Coconut Oil", "Massage into skin in circular motions", "Sol de Janeiro")
    )

    val filtered = allProducts.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    if (selectedProduct == null) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFFFFBFA)) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search products") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true
                )

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filtered) { product ->
                        DiscoverProductCard(
                            product = product,
                            isWishlisted = wishlist.contains(product),
                            onToggleWishlist = { onToggleWishlist(product) },
                            onClick = { selectedProduct = product }
                        )
                    }
                }
            }
        }
    } else {
        ProductDetailView(
            product = selectedProduct!!,
            onBack = { selectedProduct = null },
            isWishlisted = wishlist.contains(selectedProduct!!),
            onToggleWishlist = { onToggleWishlist(it) },
            onAddToCart = { onAddToCart(it) } // ✅ Diteruskan
        )
    }
}

@Composable
fun DiscoverProductCard(
    product: Product,
    isWishlisted: Boolean,
    onToggleWishlist: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clickable { onClick() }
                    .padding(end = 12.dp)
            )

            Column(modifier = Modifier.weight(1f).clickable { onClick() }) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(product.brand, color = Color.Gray, fontSize = 13.sp)
                Text(product.desc, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(product.price, color = Color(0xFFE91E63), fontWeight = FontWeight.SemiBold)
            }

            IconButton(onClick = onToggleWishlist) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Wishlist Toggle",
                    tint = if (isWishlisted) Color(0xFFE91E63) else Color.LightGray
                )
            }
        }
    }
}

@Composable
fun ProductDetailView(
    product: Product,
    onBack: () -> Unit,
    isWishlisted: Boolean,
    onToggleWishlist: (Product) -> Unit,
    onAddToCart: (Product) -> Unit // ✅ Ditambahkan
) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFFDF6F0)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = product.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(text = "by ${product.brand}", fontSize = 16.sp, color = Color.Gray)
                }
                IconButton(onClick = { onToggleWishlist(product) }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Add to Wishlist",
                        tint = if (isWishlisted) Color(0xFFE91E63) else Color.LightGray
                    )
                }
            }

            Text(text = product.desc, fontSize = 14.sp)
            Text(text = "Price: ${product.price}", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color(0xFFE91E63))
            Text(text = "Rating: ⭐⭐⭐⭐☆", fontSize = 14.sp)
            Text(text = "Ingredients:", fontWeight = FontWeight.Bold)
            Text(text = product.ingredients, fontSize = 13.sp)
            Text(text = "How to Use:", fontWeight = FontWeight.Bold)
            Text(text = product.usage, fontSize = 13.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onAddToCart(product) }, // ✅ Berfungsi
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Add to Cart")
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onBack) {
                Text("← Back to Discover")
            }
        }
    }
}
