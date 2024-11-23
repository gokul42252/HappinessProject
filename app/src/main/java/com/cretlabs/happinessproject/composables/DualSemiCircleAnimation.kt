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
fun DualSemiCircularWaveAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    // Outer circle wave animation (clockwise)
    val outerWaveOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Inner circle wave animation (counter-clockwise)
    val innerWaveOffset = infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(
        modifier = Modifier
            .size(200.dp) // Canvas size
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val outerRadius = size.width / 3 // Outer circle radius
        val innerRadius = size.width / 4 // Inner circle radius
        val dotRadius = 6.dp.toPx() // Radius of each dot
        val totalDots = 36 // Total number of dots per circle

        // Draw the outer circle
        for (i in 0 until totalDots) {
            val angle = 180f + (i * (180f / totalDots)) + outerWaveOffset.value
            val angleRad = Math.toRadians(angle.toDouble())

            val dotX = centerX + outerRadius * cos(angleRad).toFloat()
            val dotY = centerY + outerRadius * sin(angleRad).toFloat()

            val alpha = 1f - (i.toFloat() / totalDots)

            drawCircle(
                color = Color.Green.copy(alpha = alpha),
                radius = dotRadius,
                center = Offset(dotX, dotY)
            )
        }

        // Draw the inner circle
        for (i in 0 until totalDots) {
            val angle = 180f + (i * (180f / totalDots)) + innerWaveOffset.value
            val angleRad = Math.toRadians(angle.toDouble())

            val dotX = centerX + innerRadius * cos(angleRad).toFloat()
            val dotY = centerY + innerRadius * sin(angleRad).toFloat()

            val alpha = 1f - (i.toFloat() / totalDots)

            drawCircle(
                color = Color.Yellow.copy(alpha = alpha),
                radius = dotRadius,
                center = Offset(dotX, dotY)
            )
        }
    }
}

@Composable
fun DualSemiCircularWaveWithText() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Black background
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DualSemiCircularWaveAnimation()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "loading",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}