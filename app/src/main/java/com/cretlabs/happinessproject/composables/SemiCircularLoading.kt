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
fun SemiCircularWaveAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    // Animate the wave motion
    val waveOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
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
        val radius = size.width / 3 // Radius of the semicircle wave
        val dotRadius = 6.dp.toPx() // Radius of each dot
        val totalDots = 36 // Total number of dots forming the semicircle

        for (i in 0 until totalDots) {
            // Adjust angle to cover only the lower semicircle (180° to 360°)
            val angle = 180f + (i * (180f / totalDots)) + waveOffset.value
            val angleRad = Math.toRadians(angle.toDouble()) // Convert angle to radians

            // Calculate the position of each dot
            val dotX = centerX + radius * cos(angleRad).toFloat()
            val dotY = centerY + radius * sin(angleRad).toFloat()

            // Adjust the opacity for a fading effect
            val alpha = 1f - (i.toFloat() / totalDots)

            // Draw each dot at the calculated position
            drawCircle(
                color = Color.Gray.copy(alpha = alpha),
                radius = dotRadius,
                center = Offset(dotX, dotY) // Correct use of Offset(x, y)
            )
        }
    }
}

@Composable
fun SemiCircularWaveWithText() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Black background
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SemiCircularWaveAnimation()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "loading",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}