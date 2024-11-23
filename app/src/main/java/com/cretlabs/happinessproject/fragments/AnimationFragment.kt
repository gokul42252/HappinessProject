package com.cretlabs.happinessproject.fragments


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AnimationFragment(navController: NavController) {
    val animations = listOf(
        "Circular Animation",
        "WaveLoading Animation",
        "CircularWaveWithText",
        "SemiCircularWaveAnimation",
        "DualSemiCircularWaveWithText",
        "AdvancedLoaderAnimation",
        "Rotating Bulging Animation",
        "CircularLoaderAnimation",
        "Two Dot Loading Animation",
        "RotatingArcStackAnimation",
        "DualCircularLoader",
        "VibgyorLoader",
        "FireworksAnimation",
        "FireworksAnimationGlow",
        "FireworksAnimationLine"
    )

    LazyColumn {
        items(animations.size) { index ->
            BasicText(
                text = animations[index],
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { navController.navigate("animation/$index") }
            )
            Divider()
        }
    }
}
