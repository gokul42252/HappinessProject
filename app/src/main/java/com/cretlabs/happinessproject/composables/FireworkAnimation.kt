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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun FireworksAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    // Animation for firework scale (expanding effect)
    val scales = List(5) { index ->
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
    val trailProgress = List(5) { index ->
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
        List(5) {
            Offset(
                x = Random.nextFloat() * 800f, // Replace 800 with your canvas width
                y = Random.nextFloat() * 600f // Replace 600 with your canvas height
            )
        }
    }

    // Random colors for fireworks
    val fireworksColors = remember {
        List(5) {
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

        for (i in 0 until 5) {
            // Draw the ascending trail (rocket line)
            val trailStartY = canvasHeight // Start from the bottom of the screen
            val trailEndY = canvasHeight - (canvasHeight - fireworksPositions[i].y) * trailProgress[i].value

            drawLine(
                color = fireworksColors[i],
                start = Offset(fireworksPositions[i].x, trailStartY), // Start at the bottom
                end = Offset(fireworksPositions[i].x, trailEndY), // Animate upward toward firework center
                strokeWidth = 4f,
                alpha = 1f - trailProgress[i].value // Gradually fades out as it "reaches" the firework
            )

            // Draw the expanding firework circle
            if (trailProgress[i].value >= 0.9f) { // Start firework after trail reaches the center
                drawCircle(
                    color = fireworksColors[i],
                    radius = scales[i].value * 200, // Circle grows with animation
                    center = fireworksPositions[i],
                    alpha = 0.5f * (1 - scales[i].value) // Fades out as it grows
                )

                // Add sparkling particle effect
                if (scales[i].value > 0.5f) { // Trigger particles after the firework "cracks"
                    for (j in 0..50) { // Number of particles
                        val particleRadius = Random.nextFloat() * 5f // Random particle size
                        val angle = Random.nextFloat() * 360f // Random direction
                        val distance = scales[i].value * Random.nextFloat() * 150f // Distance from center
                        val particleX = fireworksPositions[i].x + distance * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
                        val particleY = fireworksPositions[i].y + distance * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()

                        drawCircle(
                            color = fireworksColors[i].copy(alpha = Random.nextFloat()), // Randomize alpha for sparkling effect
                            radius = particleRadius,
                            center = Offset(particleX, particleY)
                        )
                    }
                }
            }
        }
    }
}