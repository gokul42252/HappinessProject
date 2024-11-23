package com.cretlabs.happinessproject.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoaderAnimation() {
    // Animation for rotation and opacity
    val infiniteTransition = rememberInfiniteTransition()

    // Animating opacity for fading effect
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                1f at 0 with LinearEasing // Fully visible
                0f at 600 with LinearEasing // Fading out
                1f at 1200 with LinearEasing // Fade in again
            }
        )
    )

    // Animating the movement of the circles
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing)
        )
    )

    // Animating the expansion and contraction of circles
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                0.5f at 0 with LinearEasing // Smaller circles
                1f at 600 with LinearEasing // Larger circles
                0.5f at 1200 with LinearEasing // Back to small circles
            }
        )
    )

    // VIBGYOR colors
    val colors = listOf(
        Color(0xFF8B00FF), // Violet
        Color(0xFF4B0082), // Indigo
        Color(0xFF0000FF), // Blue
        Color(0xFF00FF00), // Green
        Color(0xFFFFFF00), // Yellow
        Color(0xFFFFA500), // Orange
        Color(0xFFFF0000)  // Red
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = Modifier.size(150.dp)) {
            val radius = size.minDimension / 2 * scale
            val numberOfCircles = colors.size // 7 circles
            val angleIncrement = 360f / numberOfCircles

            // Draw the circles in a circular layout
            for (i in colors.indices) {
                val angle = rotation + angleIncrement * i
                val x = (size.minDimension / 2 + radius * kotlin.math.cos(Math.toRadians(angle.toDouble()))).toFloat()
                val y = (size.minDimension / 2 + radius * kotlin.math.sin(Math.toRadians(angle.toDouble()))).toFloat()

                // Draw the moving dots with VIBGYOR colors
                drawCircle(
                    color = colors[i].copy(alpha = alpha), // Assign color from the VIBGYOR list
                    radius = 10f,
                    center = Offset(x, y)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Loading",
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}
