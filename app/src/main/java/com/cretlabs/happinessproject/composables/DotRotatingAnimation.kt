package com.cretlabs.happinessproject.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

@Composable
fun FullScreenRotatingPieChart() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RotatingPieChart()
    }
}

@Composable
fun RotatingPieChart() {
    val infiniteTransition = rememberInfiniteTransition()

    // Rotations for arcs
    val rotationAngles = List(5) { index ->
        infiniteTransition.animateFloat(
            initialValue = index * -72f, // Start hidden off-screen
            targetValue = 360f + index * 72f, // Full rotation
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000, // Increased speed
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    // Equal size for each arc
    val equalArcSize = 72f // Each arc occupies 1/5th of the circle

    // Colors for each arc
    val colors = listOf(
        Color(0xFFE91E63), // Pink
        Color(0xFFFFEB3B), // Yellow
        Color(0xFF2196F3), // Blue
        Color(0xFFFF9800), // Orange
        Color(0xFF9C27B0)  // Purple
    )

    Canvas(modifier = Modifier.fillMaxSize(0.6f)) {
        drawEqualRotatingPieChart(
            arcSize = equalArcSize,
            colors = colors,
            rotations = rotationAngles.map { it.value }
        )
    }
}

fun DrawScope.drawEqualRotatingPieChart(
    arcSize: Float,
    colors: List<Color>,
    rotations: List<Float>
) {
    val radius = size.minDimension / 2

    // Draw arcs with equal sizes
    colors.forEachIndexed { index, color ->
        drawArc(
            color = color,
            startAngle = rotations[index],
            sweepAngle = arcSize,
            useCenter = true,
            size = androidx.compose.ui.geometry.Size(
                width = radius * 2,
                height = radius * 2
            ),
            topLeft = center - androidx.compose.ui.geometry.Offset(radius, radius)
        )
    }
}
