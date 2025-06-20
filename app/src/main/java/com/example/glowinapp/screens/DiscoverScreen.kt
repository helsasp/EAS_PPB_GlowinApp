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
    onToggleWishlist: (Product) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val allProducts = listOf(
        Product(
            name = "Lip Glow Oil",
            brand = "Dior",
            desc = "Nourishing glossy oil",
            price = "$38",
            imageRes = R.drawable.product1,
            ingredients = "Cherry Oil, Glycerin, Tocopherol",
            usage = "Apply directly on lips for glossy shine"
        ),
        Product(
            name = "Tinted Moisturizer",
            brand = "Laura Mercier",
            desc = "Natural skin tint with SPF",
            price = "$48",
            imageRes = R.drawable.product2,
            ingredients = "Titanium Dioxide, Vitamin E",
            usage = "Blend with fingertips on clean skin"
        ),
        Product(
            name = "Lip Kit",
            brand = "Kylie Cosmetics",
            desc = "Matte lipstick & liner",
            price = "$29",
            imageRes = R.drawable.product3,
            ingredients = "Dimethicone, Iron Oxides",
            usage = "Outline lips with liner, fill with lipstick"
        ),
        Product(
            name = "Concealer",
            brand = "NARS",
            desc = "Radiant creamy coverage",
            price = "$32",
            imageRes = R.drawable.product4,
            ingredients = "Water, Mica, Glycerin",
            usage = "Dab under eyes and blend gently"
        ),
        Product(
            name = "Lip Sleeping Mask",
            brand = "Laneige",
            desc = "Hydrating overnight lip mask",
            price = "$24",
            imageRes = R.drawable.product5,
            ingredients = "Berry Mix Complex™, Shea Butter",
            usage = "Apply before bed on clean lips"
        ),
        Product(
            name = "Glow Recipe Watermelon Glow",
            brand = "Glow Recipe",
            desc = "Dewy serum for glowing skin",
            price = "$39",
            imageRes = R.drawable.product6,
            ingredients = "Watermelon Extract, Hyaluronic Acid",
            usage = "Pat gently onto skin after toner"
        ),
        Product(
            name = "Rare Beauty Blush",
            brand = "Rare Beauty",
            desc = "Soft pinch liquid blush",
            price = "$23",
            imageRes = R.drawable.product7,
            ingredients = "Mica, Dimethicone",
            usage = "Apply 1-2 dots and blend"
        ),
        Product(
            name = "Charlotte Tilbury Airbrush Flawless",
            brand = "Charlotte Tilbury",
            desc = "Full-coverage matte foundation",
            price = "$49",
            imageRes = R.drawable.product8,
            ingredients = "Silica, Glycerin, Vitamin C",
            usage = "Apply evenly using brush or sponge"
        ),
        Product(
            name = "Anastasia Brow Wiz",
            brand = "Anastasia Beverly Hills",
            desc = "Precise brow pencil",
            price = "$25",
            imageRes = R.drawable.product9,
            ingredients = "Iron Oxides, Beeswax",
            usage = "Fill in sparse brow areas"
        ),
        Product(
            name = "Sol de Janeiro Brazilian Bum Bum Cream",
            brand = "Sol de Janeiro",
            desc = "Fast-absorbing body cream",
            price = "$48",
            imageRes = R.drawable.product10,
            ingredients = "Guaraná, Cupuaçu Butter, Coconut Oil",
            usage = "Massage into skin in circular motions"
        )
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
            onToggleWishlist = { onToggleWishlist(it) }
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
    onToggleWishlist: (Product) -> Unit
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
                onClick = { /* Add to cart logic */ },
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