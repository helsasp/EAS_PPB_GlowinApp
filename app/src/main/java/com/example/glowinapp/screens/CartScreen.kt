package com.example.glowinapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun CartScreen(
    cart: List<Product>,
    onIncreaseQuantity: (Product) -> Unit,
    onDecreaseQuantity: (Product) -> Unit
) {
    val totalPrice = remember(cart) {
        cart.sumOf {
            val cleanPrice = it.price.replace("$", "").toDoubleOrNull() ?: 0.0
            cleanPrice * it.quantity
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFFFF5F2)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Your Cart", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cart) { product ->
                    CartItem(
                        product = product,
                        onIncreaseQuantity = onIncreaseQuantity,
                        onDecreaseQuantity = onDecreaseQuantity
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total: $${String.format("%.2f", totalPrice)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Checkout logic here */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continue to Payment")
            }
        }
    }
}

@Composable
fun CartItem(
    product: Product,
    onIncreaseQuantity: (Product) -> Unit,
    onDecreaseQuantity: (Product) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier.size(70.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(product.brand, fontSize = 13.sp, color = Color.Gray)
                Text("Price: ${product.price}", fontSize = 14.sp, color = Color(0xFFE91E63))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onDecreaseQuantity(product) }) {
                    Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Text(product.quantity.toString(), fontSize = 16.sp)
                IconButton(onClick = { onIncreaseQuantity(product) }) {
                    Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
