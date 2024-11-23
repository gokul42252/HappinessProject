package com.cretlabs.happinessproject.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun AdvancedLoaderAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    // Animating opacity for fade in/out
    val segmentAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                0f at 0 with LinearEasing // Fully transparent
                1f at 600 with LinearEasing // Fully visible
                0f at 1200 with LinearEasing // Fade out
            }
        )
    )

    // Animating stroke width to shrink and expand
    val strokeWidth by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                1f at 0 with LinearEasing // Thin stroke
                8f at 600 with LinearEasing // Thick stroke
                1f at 1200 with LinearEasing // Thin stroke again
            }
        )
    )

    // Animating sweep angle to reduce arc length
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 90f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                90f at 0 with LinearEasing // Full arc
                30f at 600 with LinearEasing // Short arc
                90f at 1200 with LinearEasing // Full arc again
            }
        )
    )

    // Rotating arcs independently
    val rotationAngle1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing)
        )
    )

    val rotationAngle2 by infiniteTransition.animateFloat(
        initialValue = 120f,
        targetValue = 480f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing)
        )
    )

    val rotationAngle3 by infiniteTransition.animateFloat(
        initialValue = 240f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = Modifier.size(100.dp)) {
            val radius = size.minDimension / 2 - strokeWidth

            // Draw a static black circle as background
            drawCircle(
                color = Color.White,
                radius = radius
            )

            // Segment 1
            drawArc(
                color = Color.Yellow.copy(alpha = segmentAlpha),
                startAngle = rotationAngle1,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.dp.toPx(), cap = StrokeCap.Round)
            )

            // Segment 2
            drawArc(
                color = Color.Magenta.copy(alpha = segmentAlpha),
                startAngle = rotationAngle2,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.dp.toPx(), cap = StrokeCap.Round)
            )

            // Segment 3
            drawArc(
                color = Color.Green.copy(alpha = segmentAlpha),
                startAngle = rotationAngle3,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Loading...",
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}