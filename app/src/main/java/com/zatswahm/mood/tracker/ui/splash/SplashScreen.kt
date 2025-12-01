package com.zatswahm.mood.tracker.ui.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zatswahm.mood.tracker.ui.theme.MoodTrackerTheme
import kotlinx.coroutines.delay

/**
 * Entry point for Splash feature.
 * A simple timed splash that will later be extended to check onboarding state, auth, etc.
 */
@Composable
fun SplashRoute(
    onFinished: () -> Unit,
) {
    // Simulate loading / startup work
    LaunchedEffect(Unit) {
        delay(1800L)
        onFinished()
    }
    SplashScreen()
}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            val infiniteTransition = rememberInfiniteTransition(label = "splash_progress")
            val progress by infiniteTransition.animateFloat(
                initialValue = 0.15f,
                targetValue = 0.95f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 900, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "progress_anim"
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Unified Women’s Health",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Tracking cycles, pregnancy, and medicines in one gentle place.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                )
                CircularProgressIndicator(progress = { progress })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreviewLight() {
    MoodTrackerTheme(darkTheme = false) {
        SplashScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreviewDark() {
    MoodTrackerTheme(darkTheme = true) {
        SplashScreen()
    }
}


