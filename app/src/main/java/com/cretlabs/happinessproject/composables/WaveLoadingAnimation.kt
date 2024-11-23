package com.cretlabs.happinessproject.composables

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun WaveLoadingAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    // Animate wave movement
    val waveOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Draw waves
    Canvas(
        modifier = Modifier
            .size(120.dp)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val dotRadius = 4.dp.toPx()
        val spacing = 10.dp.toPx()
        val amplitude = 20.dp.toPx() // Wave amplitude

        // Primary wave (rotated further counterclockwise)
        for (i in 0..15) {
            val x = centerX - (7 * spacing) + (i * spacing) // Evenly spaced horizontally
            val y = centerY - sin(Math.toRadians((waveOffset.value + (i * 20)).toDouble())).toFloat() * amplitude

            val alpha = 1f - (i / 15f) // Fade effect from left to right

            drawCircle(
                color = Color.Green.copy(alpha = alpha),
                radius = dotRadius,
                center = Offset(x, y)
            )
        }

        // Opposite wave (rotated further counterclockwise)
        for (i in 0..15) {
            val x = centerX - (7 * spacing) + (i * spacing) // Same horizontal spacing
            val y = centerY - sin(Math.toRadians((waveOffset.value - (i * 20)).toDouble())).toFloat() * amplitude

            val alpha = 1f - (i / 15f) // Fade effect from left to right

            drawCircle(
                color = Color.Yellow.copy(alpha = alpha), // Different color for distinction
                radius = dotRadius,
                center = Offset(x, y)
            )
        }
    }
}

@Composable
fun WaveLoadingWithText() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Black background
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WaveLoadingAnimation()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "loading",
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

