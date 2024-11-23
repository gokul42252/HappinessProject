package com.cretlabs.happinessproject.composables

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

@Composable
fun LoadingAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    // Animate rotation clockwise
    val clockwiseRotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Animate rotation counterclockwise
    val counterclockwiseRotation = infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Animation Canvas
    Box(
        modifier = Modifier
            .size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Clockwise circle
            rotate(clockwiseRotation.value) {
                for (i in 0..35) { // Draw 36 dots
                    val angle = i * 10f // Each dot is spaced by 10 degrees
                    val alpha = 1f - (i / 36f) // Fade effect
                    val radius = 50.dp.toPx() // Circle radius
                    val dotRadius = 4.dp.toPx() // Dot size

                    // Calculate dot positions using trigonometry
                    val x = center.x + radius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
                    val y = center.y + radius * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()

                    drawCircle(
                        color = Color.Green.copy(alpha = alpha),
                        radius = dotRadius,
                        center = Offset(x, y)
                    )
                }
            }

            // Counterclockwise circle
            rotate(counterclockwiseRotation.value) {
                for (i in 0..35) { // Draw 36 dots
                    val angle = i * 10f // Each dot is spaced by 10 degrees
                    val alpha = 1f - (i / 36f) // Fade effect
                    val radius = 50.dp.toPx() // Circle radius
                    val dotRadius = 4.dp.toPx() // Dot size

                    // Calculate dot positions using trigonometry
                    val x = center.x + radius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
                    val y = center.y + radius * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()

                    drawCircle(
                        color = Color.Yellow.copy(alpha = alpha), // Different color for distinction
                        radius = dotRadius,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingWithText() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White), // Set white background
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingAnimation()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "loading",
            color = Color.Gray, // Light gray text
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


