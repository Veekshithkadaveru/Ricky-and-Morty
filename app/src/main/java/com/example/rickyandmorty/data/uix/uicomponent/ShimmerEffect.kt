package com.example.rickyandmorty.data.uix.uicomponent

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {

    val shimmerTranslateAnim = rememberInfiniteTransition(label = "Shimmer Animation")
    val shimmerOffset by shimmerTranslateAnim.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Shimmer Offset"
    )

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f), // Light gray with some transparency
        Color.LightGray.copy(alpha = 0.2f), // Lighter gray with more transparency
        Color.LightGray.copy(alpha = 0.6f)  // Light gray again for the shimmering effect
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = shimmerOffset, y = 0f),
        end = Offset(x = shimmerOffset + 300f, y = 0f)
    )

    Box(modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .background(brush))
}