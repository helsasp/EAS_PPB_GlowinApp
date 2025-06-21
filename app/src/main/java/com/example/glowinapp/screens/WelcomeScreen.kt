package com.example.glowinapp.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavHostController) {
    var logoVisible by remember { mutableStateOf(false) }
    var textVisible by remember { mutableStateOf(false) }
    var buttonVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        logoVisible = true
        delay(800)
        textVisible = true
        delay(600)
        buttonVisible = true
    }

    val gradientBrush = Brush.radialGradient(
        colors = listOf(
            Color(0xFFE91E63),
            Color(0xFFF8BBD9),
            Color(0xFFFDF6F0),
            Color(0xFFFFFFFF)
        ),
        radius = 1200f
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
        ) {
            // Floating Sparkles Background
            SparklesBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Animated Logo Section
                AnimatedVisibility(
                    visible = logoVisible,
                    enter = scaleIn(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ) + fadeIn(animationSpec = tween(1000))
                ) {
                    BeautifulLogo()
                }

                // Animated Text Section
                AnimatedVisibility(
                    visible = textVisible,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(800)
                    ) + fadeIn(animationSpec = tween(800))
                ) {
                    WelcomeTextSection()
                }

                // Animated Button
                AnimatedVisibility(
                    visible = buttonVisible,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(600)
                    ) + fadeIn(animationSpec = tween(600))
                ) {
                    GlowinButton { navController.navigate("home") }
                }
            }
        }
    }
}

@Composable
fun BeautifulLogo() {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer Glow Ring
        Box(
            modifier = Modifier
                .size(180.dp)
                .rotate(rotationAngle)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFE91E63).copy(alpha = glowAlpha),
                            Color(0xFFE91E63).copy(alpha = glowAlpha * 0.5f),
                            Color.Transparent
                        ),
                        radius = 200f
                    ),
                    shape = CircleShape
                )
        )

        // Main Logo Circle
        Card(
            modifier = Modifier.size(140.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFE91E63),
                                Color(0xFFAD1457)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "G",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "✨",
                        fontSize = 24.sp
                    )
                }
            }
        }

        // Inner Sparkle
        Box(
            modifier = Modifier
                .size(60.dp)
                .rotate(-rotationAngle * 1.5f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.8f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
    }
}

@Composable
fun WelcomeTextSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Welcome to",
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = Color(0xFF3B2F2F).copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Glowin'",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE91E63),
            textAlign = TextAlign.Center,
            modifier = Modifier.graphicsLayer {
                shadowElevation = 8.dp.toPx()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Where your beauty shines brighter ✨",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = Color(0xFF6D4C41),
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Discover premium beauty products curated just for you",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF6D4C41).copy(alpha = 0.7f),
            lineHeight = 20.sp
        )
    }
}

@Composable
fun GlowinButton(onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "button_glow")
    val buttonGlow by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "button_glow"
    )

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE91E63)
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .scale(buttonGlow)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFE91E63),
                        Color(0xFFAD1457),
                        Color(0xFFE91E63)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 12.dp,
            pressedElevation = 8.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "✨ Get Glowin' ✨",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun SparklesBackground() {
    val sparkles = remember {
        (1..15).map {
            SparkleData(
                x = (0..100).random(),
                y = (0..100).random(),
                size = (8..20).random(),
                delay = (0..3000).random()
            )
        }
    }

    sparkles.forEach { sparkle ->
        AnimatedSparkle(sparkle)
    }
}

data class SparkleData(
    val x: Int,
    val y: Int,
    val size: Int,
    val delay: Int
)

@Composable
fun AnimatedSparkle(sparkle: SparkleData) {
    val infiniteTransition = rememberInfiniteTransition(label = "sparkle")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000 + sparkle.delay,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sparkle_alpha"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500 + sparkle.delay,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sparkle_scale"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        val maxWidth = 400.dp
        val maxHeight = 800.dp
        val xPos = (maxWidth * sparkle.x / 100)
        val yPos = (maxHeight * sparkle.y / 100)

        Box(
            modifier = Modifier
                .offset(x = xPos, y = yPos)
                .size(sparkle.size.dp)
                .scale(scale)
                .background(
                    color = Color.White.copy(alpha = alpha * 0.7f),
                    shape = CircleShape
                )
        )
    }
}