package com.cretlabs.happinessproject.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun VibgyorTripleDotLoader() {
    val infiniteTransition = rememberInfiniteTransition()

    // Animation for clockwise red dots and arc
    val rotationAngleRed by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val sweepAngleRed by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = 270f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1500
                20f at 0
                270f at 750 // Expands halfway
                20f at 1500 // Shrinks again
            },
            repeatMode = RepeatMode.Restart
        )
    )

    // Animation for anti-clockwise blue dots and arc
    val rotationAngleBlue by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f, // Reverse direction
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val sweepAngleBlue by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = 270f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1500
                20f at 0
                270f at 750
                20f at 1500
            },
            repeatMode = RepeatMode.Restart
        )
    )

    // Define VIBGYOR colors
    val vibgyorColors = listOf(
        Color(0xFF8A2BE2), // Violet
        Color(0xFF4B0082), // Indigo
        Color(0xFF0000FF), // Blue
        Color(0xFF008000), // Green
        Color(0xFFFFFF00), // Yellow
        Color(0xFFFFA500), // Orange
        Color(0xFFFF0000)  // Red
    )

    Canvas(
        modifier = Modifier
            .size(200.dp) // Adjust canvas size
    ) {
        val radius = size.minDimension / 3.5f // Circle radius
        val center = Offset(size.width / 2, size.height / 2)

        // Helper function to calculate dot positions
        fun calculateDotPosition(angle: Float): Offset {
            val x = center.x + radius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = center.y + radius * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
            return Offset(x, y)
        }

        // Draw red trailing arc
        drawArc(
            color = Color.Red,
            startAngle = rotationAngleRed - sweepAngleRed / 2,
            sweepAngle = sweepAngleRed,
            useCenter = false,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round), // Reduced stroke width
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        // Draw blue trailing arc
        drawArc(
            color = Color.Blue,
            startAngle = rotationAngleBlue - sweepAngleBlue / 2,
            sweepAngle = sweepAngleBlue,
            useCenter = false,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round), // Reduced stroke width
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        // Draw red circle dots (VIBGYOR color sequence)
        for (i in 0 until 3) {
            val angle = rotationAngleRed + i * 120f
            val position = calculateDotPosition(angle)
            drawCircle(
                color = vibgyorColors[i % vibgyorColors.size],
                radius = 8.dp.toPx(), // Dot size
                center = position
            )
        }

        // Draw blue circle dots (VIBGYOR color sequence)
        for (i in 0 until 3) {
            val angle = rotationAngleBlue + i * 120f
            val position = calculateDotPosition(angle)
            drawCircle(
                color = vibgyorColors[(i + 3) % vibgyorColors.size], // Offset colors for variety
                radius = 12.dp.toPx(), // Dot size
                center = position
            )
        }
    }
}
@Composable
fun VibgyorLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        VibgyorTripleDotLoader()
    }
}