package com.cretlabs.happinessproject.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RotatingConcentricCircles() {
    // Infinite transitions for rotation
    val infiniteTransition = rememberInfiniteTransition()

    // Rotations for each circle with varying speeds
    val rotations = listOf(
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 4000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        ),
        infiniteTransition.animateFloat(
            initialValue = 360f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        ),
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        ),
        infiniteTransition.animateFloat(
            initialValue = 360f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 6000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    )

    Canvas(
        modifier = Modifier
            .size(200.dp) // Define the size of the canvas
    ) {
        val center = Offset(size.width / 2, size.height / 2) // Center of the canvas
        val baseRadius = size.width / 3 // Radius of the largest circle
        val dotRadius = 4.dp.toPx() // Size of each dot
        val totalDots = 36 // Number of dots in each circle

        // Draw concentric circles with decreasing radii and rotations
        for (circleIndex in 0 until 4) { // Four circles
            val radius = baseRadius - (circleIndex * (baseRadius / 4)) // Decrease radius incrementally
            val rotation = rotations[circleIndex].value // Use the animated rotation value

            for (i in 0 until totalDots) {
                val angle = i * (360f / totalDots) + rotation // Add rotation to the angle
                val angleRad = Math.toRadians(angle.toDouble()) // Convert angle to radians

                // Calculate position of the dot
                val dotX = center.x + radius * cos(angleRad).toFloat()
                val dotY = center.y + radius * sin(angleRad).toFloat()

                // Adjust opacity for fading effect
                val alpha = 1f - (circleIndex.toFloat() / 4f) // Outer circles are more opaque

                // Draw the dot
                drawCircle(
                    color = Color.White.copy(alpha = alpha),
                    radius = dotRadius,
                    center = Offset(dotX, dotY)
                )
            }
        }
    }
}

@Composable
fun RotatingConcentricCirclesWithText() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // Set black background
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RotatingConcentricCircles()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "loading",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}