package com.example.glowinapp.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.glowinapp.model.Product
import com.example.glowinapp.R

data class PaymentMethod(
    val name: String,
    val icon: Any, // Can be ImageVector or Int (drawable resource)
    val description: String,
    val isSelected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavHostController,
    cart: List<Product>,
    totalAmount: Double
) {
    var selectedPaymentMethod by remember { mutableStateOf(0) }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val paymentMethods = listOf(
        PaymentMethod("Credit Card", R.drawable.ic_credit_card, "Visa, Mastercard, AMEX"),
        PaymentMethod("Digital Wallet", R.drawable.ic_account_balance_wallet, "GoPay, OVO, DANA"),
        PaymentMethod("Bank Transfer", R.drawable.ic_account_balance, "BCA, BRI, Mandiri"),
        PaymentMethod("Buy Now Pay Later", R.drawable.ic_schedule, "Kredivo, Akulaku")
    )

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFDF6F0),
            Color(0xFFFFFFFF)
        )
    )

    if (showSuccessDialog) {
        PaymentSuccessDialog(
            onDismiss = {
                showSuccessDialog = false
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        // Header
        TopAppBar(
            title = {
                Text(
                    "Secure Payment",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B2F2F)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF3B2F2F)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { OrderSummaryCard(cart, totalAmount) }
            item { PaymentMethodSection(paymentMethods, selectedPaymentMethod) { selectedPaymentMethod = it } }

            if (selectedPaymentMethod == 0) { // Credit Card
                item {
                    CreditCardForm(
                        cardNumber = cardNumber,
                        onCardNumberChange = { cardNumber = it },
                        expiryDate = expiryDate,
                        onExpiryDateChange = { expiryDate = it },
                        cvv = cvv,
                        onCvvChange = { cvv = it },
                        cardHolderName = cardHolderName,
                        onCardHolderNameChange = { cardHolderName = it }
                    )
                }
            }

            item {
                ShippingAddressSection(
                    address = address,
                    onAddressChange = { address = it },
                    city = city,
                    onCityChange = { city = it },
                    zipCode = zipCode,
                    onZipCodeChange = { zipCode = it }
                )
            }

            item { PromoCodeSection() }

            item {
                PaymentButton(
                    totalAmount = totalAmount,
                    onPayment = { showSuccessDialog = true }
                )
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun OrderSummaryCard(cart: List<Product>, totalAmount: Double) {
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
                modifier = Modifier.padding(bottom = 16.dp)
            )

            cart.forEach { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${product.name} (${product.quantity}x)",
                        fontSize = 14.sp,
                        color = Color(0xFF3B2F2F),
                        modifier = Modifier.weight(1f)
                    )
                    val itemPrice = product.price.replace("$", "").toDoubleOrNull() ?: 0.0
                    Text(
                        text = "$${String.format("%.2f", itemPrice * product.quantity)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF3B2F2F)
                    )
                }
            }

            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = Color.LightGray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Subtotal:", fontSize = 14.sp, color = Color.Gray)
                Text("$${String.format("%.2f", totalAmount)}", fontSize = 14.sp, color = Color.Gray)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Shipping:", fontSize = 14.sp, color = Color.Gray)
                Text("FREE", fontSize = 14.sp, color = Color(0xFFE91E63), fontWeight = FontWeight.Bold)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Gold Member Discount:", fontSize = 14.sp, color = Color.Gray)
                Text("-$${String.format("%.2f", totalAmount * 0.15)}", fontSize = 14.sp, color = Color(0xFFE91E63))
            }

            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.LightGray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B2F2F)
                )
                Text(
                    text = "$${String.format("%.2f", totalAmount * 0.85)}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE91E63)
                )
            }
        }
    }
}

@Composable
fun PaymentMethodSection(
    paymentMethods: List<PaymentMethod>,
    selectedMethod: Int,
    onMethodSelected: (Int) -> Unit
) {
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
                text = "Payment Method",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3B2F2F),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            paymentMethods.forEachIndexed { index, method ->
                PaymentMethodItem(
                    method = method,
                    isSelected = selectedMethod == index,
                    onClick = { onMethodSelected(index) }
                )
                if (index != paymentMethods.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun PaymentMethodItem(
    method: PaymentMethod,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFE91E63).copy(alpha = 0.1f) else Color(0xFFF8F8F8)
        ),
        shape = RoundedCornerShape(12.dp),
        border = if (isSelected) BorderStroke(2.dp, Color(0xFFE91E63)) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (method.icon) {
                is ImageVector -> {
                    Icon(
                        imageVector = method.icon,
                        contentDescription = method.name,
                        tint = if (isSelected) Color(0xFFE91E63) else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
                is Int -> {
                    Icon(
                        painter = painterResource(id = method.icon),
                        contentDescription = method.name,
                        tint = if (isSelected) Color(0xFFE91E63) else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = method.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) Color(0xFFE91E63) else Color(0xFF3B2F2F)
                )
                Text(
                    text = method.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            RadioButton(
                selected = isSelected,
                onClick = { onClick() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFFE91E63)
                )
            )
        }
    }
}

@Composable
fun CreditCardForm(
    cardNumber: String,
    onCardNumberChange: (String) -> Unit,
    expiryDate: String,
    onExpiryDateChange: (String) -> Unit,
    cvv: String,
    onCvvChange: (String) -> Unit,
    cardHolderName: String,
    onCardHolderNameChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Card Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3B2F2F)
            )

            OutlinedTextField(
                value = cardHolderName,
                onValueChange = onCardHolderNameChange,
                label = { Text("Cardholder Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE91E63),
                    focusedLabelColor = Color(0xFFE91E63)
                )
            )

            OutlinedTextField(
                value = cardNumber,
                onValueChange = { if (it.length <= 19) onCardNumberChange(it) },
                label = { Text("Card Number") },
                placeholder = { Text("1234 5678 9012 3456") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE91E63),
                    focusedLabelColor = Color(0xFFE91E63)
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_credit_card),
                        contentDescription = "Credit Card",
                        tint = Color(0xFFE91E63)
                    )
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { if (it.length <= 5) onExpiryDateChange(it) },
                    label = { Text("MM/YY") },
                    placeholder = { Text("12/25") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE91E63),
                        focusedLabelColor = Color(0xFFE91E63)
                    )
                )

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { if (it.length <= 3) onCvvChange(it) },
                    label = { Text("CVV") },
                    placeholder = { Text("123") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE91E63),
                        focusedLabelColor = Color(0xFFE91E63)
                    )
                )
            }

            // Security notice
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE91E63).copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Secure",
                        tint = Color(0xFFE91E63),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Your payment information is encrypted and secure",
                        fontSize = 12.sp,
                        color = Color(0xFF3B2F2F)
                    )
                }
            }
        }
    }
}

@Composable
fun ShippingAddressSection(
    address: String,
    onAddressChange: (String) -> Unit,
    city: String,
    onCityChange: (String) -> Unit,
    zipCode: String,
    onZipCodeChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_shipping),
                    contentDescription = "Shipping",
                    tint = Color(0xFFE91E63),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Shipping Address",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B2F2F)
                )
            }

            OutlinedTextField(
                value = address,
                onValueChange = onAddressChange,
                label = { Text("Street Address") },
                placeholder = { Text("123 Main Street") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE91E63),
                    focusedLabelColor = Color(0xFFE91E63)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = onCityChange,
                    label = { Text("City") },
                    placeholder = { Text("Jakarta") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE91E63),
                        focusedLabelColor = Color(0xFFE91E63)
                    )
                )

                OutlinedTextField(
                    value = zipCode,
                    onValueChange = onZipCodeChange,
                    label = { Text("ZIP Code") },
                    placeholder = { Text("12345") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE91E63),
                        focusedLabelColor = Color(0xFFE91E63)
                    )
                )
            }

            // Estimated delivery
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_local_shipping),
                        contentDescription = "Delivery",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Estimated Delivery",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3B2F2F)
                        )
                        Text(
                            text = "2-3 business days • FREE shipping",
                            fontSize = 11.sp,
                            color = Color(0xFF4CAF50)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PromoCodeSection() {
    var promoCode by remember { mutableStateOf("") }
    var isApplied by remember { mutableStateOf(false) }

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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_discount),
                    contentDescription = "Promo",
                    tint = Color(0xFFE91E63),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Promo Code",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B2F2F)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = promoCode,
                    onValueChange = { promoCode = it.uppercase() },
                    label = { Text("Enter promo code") },
                    placeholder = { Text("GLOW2024") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isApplied,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE91E63),
                        focusedLabelColor = Color(0xFFE91E63)
                    )
                )

                Button(
                    onClick = {
                        if (promoCode.isNotEmpty()) {
                            isApplied = true
                        }
                    },
                    enabled = promoCode.isNotEmpty() && !isApplied,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63)
                    ),
                    modifier = Modifier.height(56.dp)
                ) {
                    Text(
                        text = if (isApplied) "Applied ✓" else "Apply",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (isApplied) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🎉",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Promo code applied! Extra 5% off",
                            fontSize = 14.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Suggested promo codes
            if (!isApplied) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Try these codes:",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("GLOW2024", "BEAUTY15", "NEWBIE").forEach { code ->
                        Card(
                            modifier = Modifier.clickable {
                                promoCode = code
                                isApplied = true
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE91E63).copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = code,
                                fontSize = 10.sp,
                                color = Color(0xFFE91E63),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentButton(
    totalAmount: Double,
    onPayment: () -> Unit
) {
    val finalAmount = totalAmount * 0.85 // After 15% discount

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Button(
            onClick = onPayment,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFE91E63),
                            Color(0xFFAD1457)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 4.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Secure",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Pay Securely",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${String.format("%.2f", finalAmount)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_security),
                    contentDescription = "Security",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun PaymentSuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(24.dp),
        containerColor = Color.White,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Success animation placeholder
                Card(
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4CAF50).copy(alpha = 0.2f)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓",
                            fontSize = 36.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Payment Successful! 🎉",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B2F2F),
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your order has been confirmed and will be shipped soon. Get ready to glow! ✨",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFDF6F0)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Order Number",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "#GL${System.currentTimeMillis().toString().takeLast(6)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE91E63)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Divider(color = Color.LightGray.copy(alpha = 0.5f))

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Estimated Delivery",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "2-3 business days",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF3B2F2F)
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "Tracking",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Available soon",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE91E63)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "Email",
                        tint = Color(0xFFE91E63),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Receipt sent to your email",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_shopping_bag_outline),
                            contentDescription = "Continue Shopping",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Continue Shopping",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                OutlinedButton(
                    onClick = { /* Navigate to order tracking */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE91E63)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFE91E63)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_visibility),
                            contentDescription = "Track Order",
                            tint = Color(0xFFE91E63),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Track My Order",
                            color = Color(0xFFE91E63),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    )
}