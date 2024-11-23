package com.cretlabs.happinessproject.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp


@Composable
fun CircularLoaderAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    // Arc rotation animation (0 to 360 degrees)
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing)
        )
    )

    // Arc thickness animation (for pulsing effect)
    val arcThickness by infiniteTransition.animateFloat(
        initialValue = 12f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Arc opacity animation (for pulsing effect)
    val arcAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            val canvasCenter = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2)
            val circleRadius = size.minDimension / 3

            // Rotate and draw arcs
            rotate(degrees = rotationAngle, pivot = canvasCenter) {
                // Yellow Arc
                drawArc(
                    color = Color.Yellow.copy(alpha = arcAlpha),
                    startAngle = 0f,
                    sweepAngle = 240f,
                    useCenter = false,
                    topLeft = androidx.compose.ui.geometry.Offset(
                        canvasCenter.x - circleRadius,
                        canvasCenter.y - circleRadius
                    ),
                    size = androidx.compose.ui.geometry.Size(
                        width = circleRadius * 2,
                        height = circleRadius * 2
                    ),
                    style = Stroke(width = arcThickness)
                )

                // Green Arc
                drawArc(
                    color = Color.Green.copy(alpha = arcAlpha),
                    startAngle = 180f,
                    sweepAngle = 90f,
                    useCenter = false,
                    topLeft = androidx.compose.ui.geometry.Offset(
                        canvasCenter.x - circleRadius,
                        canvasCenter.y - circleRadius
                    ),
                    size = androidx.compose.ui.geometry.Size(
                        width = circleRadius * 2,
                        height = circleRadius * 2
                    ),
                    style = Stroke(width = arcThickness)
                )

                // Red Arc (completes the circle)
                drawArc(
                    color = Color.Red.copy(alpha = arcAlpha),
                    startAngle = 90f,
                    sweepAngle = 90f,
                    useCenter = false,
                    topLeft = androidx.compose.ui.geometry.Offset(
                        canvasCenter.x - circleRadius,
                        canvasCenter.y - circleRadius
                    ),
                    size = androidx.compose.ui.geometry.Size(
                        width = circleRadius * 2,
                        height = circleRadius * 2
                    ),
                    style = Stroke(width = arcThickness)
                )
            }
        }
    }
}