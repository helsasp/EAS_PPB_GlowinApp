package com.example.glowinapp.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.glowinapp.R

data class Reward(
    val title: String,
    val points: Int,
    val description: String,
    val icon: Any, // Can be ImageVector or Int (drawable resource)
    val available: Boolean = true
)

data class Transaction(
    val type: String, // "earned" or "redeemed"
    val points: Int,
    val description: String,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembershipScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Rewards", "History")

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE91E63),
            Color(0xFFAD1457),
            Color(0xFFFDF6F0)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        // Header with back button
        TopAppBar(
            title = {
                Text(
                    "Glowin' Membership",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
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
            item { MembershipStatusCard() }
            item { PointsProgressCard() }
            item { TabSection(selectedTab) { selectedTab = it } }

            when (selectedTab) {
                0 -> item { MembershipOverview() }
                1 -> item { RewardsSection() }
                2 -> item { HistorySection() }
            }
        }
    }
}

@Composable
fun MembershipStatusCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFFD700),
                            Color(0xFFFFA000)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(Color.White, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "GLOWIN' GOLD",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "Premium Member since 2024",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = " Exclusive Benefits Active",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Text(
                    text = "✨",
                    fontSize = 48.sp
                )
            }
        }
    }
}

@Composable
fun PointsProgressCard() {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Available Points",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "2,450",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE91E63)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Next Tier",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Diamond",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3B2F2F)
                    )
                    Text(
                        text = "550 points to go",
                        fontSize = 12.sp,
                        color = Color(0xFFE91E63)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = 0.82f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFFE91E63),
                trackColor = Color.LightGray.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "82% to Diamond Tier",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TabSection(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Overview", "Rewards", "History")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            indicator = { tabPositions ->
                if (selectedTab < tabPositions.size) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.BottomStart)
                            .offset(x = tabPositions[selectedTab].left)
                            .width(tabPositions[selectedTab].width)
                            .height(4.dp)
                            .background(
                                Color(0xFFE91E63),
                                RoundedCornerShape(2.dp)
                            )
                    )
                }
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { onTabSelected(index) },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == index) Color(0xFFE91E63) else Color.Gray
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MembershipOverview() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Your Gold Benefits",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3B2F2F)
            )

            BenefitItem("🎁", "Exclusive monthly gifts", "Get surprise beauty products")
            BenefitItem("💰", "Extra 15% cashback", "On every purchase you make")
            BenefitItem("🚚", "Free shipping", "No minimum purchase required")
            BenefitItem("⚡", "Early access", "New products & sales")
            BenefitItem("💬", "Priority support", "24/7 beauty consultation")
        }
    }
}

@Composable
fun BenefitItem(emoji: String, title: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = emoji,
            fontSize = 24.sp,
            modifier = Modifier.size(40.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3B2F2F)
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun RewardsSection() {
    val rewards = listOf(
        Reward("Free Lip Gloss", 500, "Get any lip gloss for free", R.drawable.ic_gift),
        Reward("20% Off Coupon", 800, "Discount for next purchase", R.drawable.ic_discount),
        Reward("Free Skincare Sample", 300, "Try new skincare products", R.drawable.ic_spa),
        Reward("VIP Consultation", 1200, "1-on-1 beauty consultation", Icons.Default.Person),
        Reward("Free Shipping Month", 1000, "Free shipping for 30 days", R.drawable.ic_shipping, false)
    )

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
                text = "Available Rewards",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3B2F2F),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            rewards.forEach { reward ->
                RewardItem(reward)
                if (reward != rewards.last()) {
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun RewardItem(reward: Reward) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (reward.icon) {
            is ImageVector -> {
                Icon(
                    imageVector = reward.icon,
                    contentDescription = reward.title,
                    tint = if (reward.available) Color(0xFFE91E63) else Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
            }
            is Int -> {
                Icon(
                    painter = painterResource(id = reward.icon),
                    contentDescription = reward.title,
                    tint = if (reward.available) Color(0xFFE91E63) else Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = reward.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (reward.available) Color(0xFF3B2F2F) else Color.Gray
            )
            Text(
                text = reward.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${reward.points} pts",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (reward.available) Color(0xFFE91E63) else Color.Gray
            )

            if (reward.available) {
                Text(
                    text = "Redeem",
                    fontSize = 12.sp,
                    color = Color(0xFFE91E63),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .background(
                            Color(0xFFE91E63).copy(alpha = 0.1f),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clickable { /* Handle redeem */ }
                )
            } else {
                Text(
                    text = "Unavailable",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun HistorySection() {
    val transactions = listOf(
        Transaction("earned", 150, "Purchase: Rare Beauty Blush", "Dec 20, 2024"),
        Transaction("redeemed", -500, "Free Lip Gloss", "Dec 18, 2024"),
        Transaction("earned", 200, "Purchase: NARS Concealer", "Dec 15, 2024"),
        Transaction("earned", 100, "Monthly Gold Bonus", "Dec 1, 2024"),
        Transaction("redeemed", -300, "Free Skincare Sample", "Nov 28, 2024")
    )

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
                text = "Points History",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3B2F2F),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            transactions.forEach { transaction ->
                TransactionItem(transaction)
                if (transaction != transactions.last()) {
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (transaction.type == "earned") Icons.Default.Add else Icons.Default.Close,
            contentDescription = transaction.type,
            tint = if (transaction.type == "earned") Color.Green else Color.Red,
            modifier = Modifier
                .size(24.dp)
                .background(
                    if (transaction.type == "earned") Color.Green.copy(alpha = 0.1f)
                    else Color.Red.copy(alpha = 0.1f),
                    CircleShape
                )
                .padding(4.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3B2F2F)
            )
            Text(
                text = transaction.date,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Text(
            text = "${if (transaction.points > 0) "+" else ""}${transaction.points}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (transaction.points > 0) Color.Green else Color.Red
        )
    }
}