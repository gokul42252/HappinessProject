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
fun DualCircularLoader() {
    val infiniteTransition = rememberInfiniteTransition()

    // Animation for clockwise red dot and arc
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

    // Animation for anti-clockwise blue dot and arc
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

    Canvas(
        modifier = Modifier
            .size(200.dp) // Adjust canvas size
    ) {
        val radius = size.minDimension / 2.5f // Circle radius
        val center = Offset(size.width / 2, size.height / 2)

        // Positions for the red dot
        val redDotX = center.x + radius * kotlin.math.cos(Math.toRadians(rotationAngleRed.toDouble())).toFloat()
        val redDotY = center.y + radius * kotlin.math.sin(Math.toRadians(rotationAngleRed.toDouble())).toFloat()

        // Positions for the blue dot
        val blueDotX = center.x + radius * kotlin.math.cos(Math.toRadians(rotationAngleBlue.toDouble())).toFloat()
        val blueDotY = center.y + radius * kotlin.math.sin(Math.toRadians(rotationAngleBlue.toDouble())).toFloat()

        // Draw the red trailing arc
        drawArc(
            color = Color.Yellow,
            startAngle = rotationAngleRed - sweepAngleRed / 2,
            sweepAngle = sweepAngleRed,
            useCenter = false,
            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        // Draw the blue trailing arc
        drawArc(
            color = Color.Green,
            startAngle = rotationAngleBlue - sweepAngleBlue / 2,
            sweepAngle = sweepAngleBlue,
            useCenter = false,
            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        // Draw the red rotating dot
        drawCircle(
            color = Color.White,
            radius = 8.dp.toPx(),
            center = Offset(redDotX, redDotY)
        )

        // Draw the blue rotating dot
        drawCircle(
            color = Color.White,
            radius = 8.dp.toPx(),
            center = Offset(blueDotX, blueDotY)
        )
    }
}
@Composable
fun MyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DualCircularLoader()
    }
}