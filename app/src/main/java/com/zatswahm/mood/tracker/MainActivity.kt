package com.zatswahm.mood.tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zatswahm.mood.tracker.ui.theme.MoodTrackerTheme
import com.zatswahm.mood.tracker.ui.navigation.AppNavHost
import com.zatswahm.mood.tracker.ui.navigation.rememberAppNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoodTrackerTheme {
                AppRoot()
            }
        }
    }
}

@Composable
private fun AppRoot() {
    val navController = rememberAppNavController()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        AppNavHost(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun AppRootPreview() {
    MoodTrackerTheme {
        AppRoot()
    }
}