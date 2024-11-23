package com.cretlabs.happinessproject.composables
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun FireworksAnimationGlow() {
    val infiniteTransition = rememberInfiniteTransition()

    // Limit to 3 active fireworks
    val activeFireworks = remember { mutableStateOf(3) }
    val fireworksCount = 3

    // Animation for firework scale (expanding effect)
    val scales = List(fireworksCount) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000 + index * 200, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    // Animation for the ascending trail (rocket line)
    val trailProgress = List(fireworksCount) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500 + index * 100, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    // Randomly generate positions for fireworks
    val fireworksPositions = remember {
        List(fireworksCount) {
            Offset(
                x = Random.nextFloat() * 800f, // Replace 800 with your canvas width
                y = Random.nextFloat() * 600f // Replace 600 with your canvas height
            )
        }
    }

    // Random colors for fireworks
    val fireworksColors = remember {
        List(fireworksCount) {
            Color(
                red = Random.nextFloat(),
                green = Random.nextFloat(),
                blue = Random.nextFloat(),
                alpha = 1f
            )
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasHeight = size.height

        for (i in 0 until activeFireworks.value) {
            // Draw the ascending trail (rocket line)
            val trailStartY = canvasHeight
            val trailEndY = canvasHeight - (canvasHeight - fireworksPositions[i].y) * trailProgress[i].value

            drawLine(
                color = fireworksColors[i],
                start = Offset(fireworksPositions[i].x, trailStartY),
                end = Offset(fireworksPositions[i].x, trailEndY),
                strokeWidth = 4f,
                alpha = 1f - trailProgress[i].value
            )

            // Draw the expanding firework circle
            if (trailProgress[i].value >= 0.9f) {
                // Glowing effect
                drawCircle(
                    color = fireworksColors[i].copy(alpha = 0.2f),
                    radius = scales[i].value * 250,
                    center = fireworksPositions[i],
                    blendMode = BlendMode.Plus
                )

                // Main burst
                drawCircle(
                    color = fireworksColors[i],
                    radius = scales[i].value * 200,
                    center = fireworksPositions[i],
                    alpha = 0.5f * (1 - scales[i].value)
                )

                // Add sparkling particle effect
                if (scales[i].value > 0.5f) {
                    val particleCount = 30 // Limit particles for better performance
                    for (j in 0 until particleCount) {
                        val particleRadius = Random.nextFloat() * 10f + 5f
                        val angle = Random.nextFloat() * 360f
                        val distance = scales[i].value * Random.nextFloat() * 150f
                        val particleX = fireworksPositions[i].x + distance * cos(Math.toRadians(angle.toDouble())).toFloat()
                        val particleY = fireworksPositions[i].y + distance * sin(Math.toRadians(angle.toDouble())).toFloat()

                        drawCircle(
                            color = fireworksColors[i].copy(alpha = 0.8f * (1 - scales[i].value)),
                            radius = particleRadius,
                            center = Offset(particleX, particleY)
                        )
                    }
                }
            }
        }
    }
}