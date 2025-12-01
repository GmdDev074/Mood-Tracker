package com.zatswahm.mood.tracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zatswahm.mood.tracker.ui.splash.SplashRoute
import com.zatswahm.mood.tracker.ui.onboarding.OnboardingRoute
import com.zatswahm.mood.tracker.ui.auth.LoginScreen
import com.zatswahm.mood.tracker.ui.auth.SignupScreen
import com.zatswahm.mood.tracker.ui.auth.ForgotPasswordScreen

/**
 * Top-level destinations for the app.
 * For now we only wire Splash -> Onboarding.
 */
enum class AppDestination {
    Splash,
    Onboarding,
    Login,
    Signup,
    ForgotPassword,
    // Home, Calendar, Reminders, Tools, Profile, etc. will come later
}

@Composable
fun rememberAppNavController(): NavHostController = rememberNavController()

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Splash.name,
        modifier = modifier
    ) {
        composable(AppDestination.Splash.name) {
            SplashRoute(
                onFinished = {
                    navController.navigate(AppDestination.Onboarding.name) {
                        popUpTo(AppDestination.Splash.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AppDestination.Onboarding.name) {
            OnboardingRoute(
                onFinished = {
                    navController.navigate(AppDestination.Login.name) {
                        popUpTo(AppDestination.Onboarding.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AppDestination.Login.name) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onSignUpClick = { navController.navigate(AppDestination.Signup.name) },
                onForgotPasswordClick = { navController.navigate(AppDestination.ForgotPassword.name) },
                onGoogleClick = { /* TODO hook Google sign-in */ }
            )
        }
        composable(AppDestination.Signup.name) {
            SignupScreen(
                onBack = { navController.popBackStack() },
                onLoginClick = {
                    navController.popBackStack(AppDestination.Login.name, inclusive = false)
                },
                onGoogleClick = { /* TODO hook Google sign-in */ }
            )
        }
        composable(AppDestination.ForgotPassword.name) {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onLoginClick = {
                    navController.popBackStack(AppDestination.Login.name, inclusive = false)
                }
            )
        }
    }
}


