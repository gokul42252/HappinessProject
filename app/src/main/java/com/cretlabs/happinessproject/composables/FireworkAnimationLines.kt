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
fun FireworksAnimationLine() {
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
                animation = tween(3000 + index * 300, easing = LinearOutSlowInEasing), // Increased duration
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
                animation = tween(2000 + index * 200, easing = FastOutSlowInEasing),
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
        val canvasWidth = size.width

        for (i in 0 until activeFireworks.value) {
            // Draw the ascending rocket line (animation towards the firework center)
            val trailStartY = canvasHeight // Start from the bottom of the screen
            val trailEndY = canvasHeight - (canvasHeight - fireworksPositions[i].y) * trailProgress[i].value

            // Draw the ascending line (rocket effect)
            drawLine(
                color = fireworksColors[i],
                start = Offset(fireworksPositions[i].x, trailStartY), // Start at the bottom
                end = Offset(fireworksPositions[i].x, trailEndY), // Animate upward toward firework center
                strokeWidth = 4f,
                alpha = 1f - trailProgress[i].value // Fade out as it reaches the burst center
            )

            // Draw the expanding firework circle
            if (trailProgress[i].value >= 0.9f) { // Start firework after trail reaches the center
                // Glowing effect at the burst position
                drawCircle(
                    color = fireworksColors[i].copy(alpha = 0.2f),
                    radius = scales[i].value * 250,
                    center = fireworksPositions[i],
                    blendMode = BlendMode.Plus
                )

                // Main burst (firework expanding circle)
                drawCircle(
                    color = fireworksColors[i],
                    radius = scales[i].value * 200,
                    center = fireworksPositions[i],
                    alpha = 0.5f * (1 - scales[i].value)
                )

                // Add bursting lines (ascending rocket-like effect before burst)
                if (scales[i].value > 0.5f) {
                    val trailLength = 250f // Increased length of the trailing lines
                    val lineCount = 15 // More lines to draw

                    for (j in 0 until lineCount) {
                        val angle = Random.nextFloat() * 360f // Random direction
                        val distance = scales[i].value * trailLength // Distance from the center of the burst
                        val lineX = fireworksPositions[i].x + distance * cos(Math.toRadians(angle.toDouble())).toFloat()
                        val lineY = fireworksPositions[i].y + distance * sin(Math.toRadians(angle.toDouble())).toFloat()

                        // Draw the trailing line (ascending rocket-like effect)
                        drawLine(
                            color = fireworksColors[i].copy(alpha = 0.6f * (1 - scales[i].value)),
                            start = fireworksPositions[i],
                            end = Offset(lineX, lineY),
                            strokeWidth = 2f
                        )

                        // Draw a dot at the end of the trailing line
                        drawCircle(
                            color = fireworksColors[i].copy(alpha = 0.8f * (1 - scales[i].value)),
                            radius = 4f, // Size of the dot
                            center = Offset(lineX, lineY)
                        )

                        // Draw the fading particle-like line (effect from burst)
                        val particleAlpha = 1f - (distance / (scales[i].value * trailLength)) // Fade effect based on distance
                        val particleLength = Random.nextFloat() * 30f // Length of the particle trail

                        drawLine(
                            color = fireworksColors[i].copy(alpha = particleAlpha),
                            start = fireworksPositions[i],
                            end = Offset(
                                lineX + particleLength * cos(Math.toRadians(angle.toDouble())).toFloat(),
                                lineY + particleLength * sin(Math.toRadians(angle.toDouble())).toFloat()
                            ),
                            strokeWidth = 1f
                        )
                    }
                }
            }
        }
    }
}