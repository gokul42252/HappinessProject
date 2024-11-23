package com.cretlabs.happinessproject.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cretlabs.happinessproject.composables.AdvancedLoaderAnimation
import com.cretlabs.happinessproject.composables.CircularLoaderAnimation
import com.cretlabs.happinessproject.composables.DualSemiCircularWaveWithText
import com.cretlabs.happinessproject.composables.FireworksAnimation
import com.cretlabs.happinessproject.composables.FireworksAnimationGlow
import com.cretlabs.happinessproject.composables.FireworksAnimationLine
import com.cretlabs.happinessproject.composables.FullScreenRotatingPieChart
import com.cretlabs.happinessproject.composables.LoaderAnimation
import com.cretlabs.happinessproject.composables.LoadingAnimation2
import com.cretlabs.happinessproject.composables.LoadingWithText
import com.cretlabs.happinessproject.composables.MyScreen
import com.cretlabs.happinessproject.composables.SemiCircularWaveWithText
import com.cretlabs.happinessproject.composables.VibgyorLoader
import com.cretlabs.happinessproject.composables.WaveLoadingWithText
import com.cretlabs.happinessproject.fragments.AnimationFragment


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "animation_list") {
        composable("animation_list") {
            AnimationFragment(navController)
        }
        composable("animation/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            when (id) {
                0 -> LoadingWithText()
                1 -> WaveLoadingWithText()
                2 -> WaveLoadingWithText()
                3 -> SemiCircularWaveWithText()
                4 -> DualSemiCircularWaveWithText()
                5 -> AdvancedLoaderAnimation()
                6 -> LoaderAnimation()
                7 -> CircularLoaderAnimation()
                8 -> LoadingAnimation2()
                9 -> FullScreenRotatingPieChart()
                10 -> MyScreen()
                11-> VibgyorLoader()
                12 -> FireworksAnimation()
                13 -> FireworksAnimationGlow()
                14 -> FireworksAnimationLine()
                else -> Text("Animation Not Found")
            }
        }
    }
}
