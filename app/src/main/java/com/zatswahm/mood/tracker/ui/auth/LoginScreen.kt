package com.zatswahm.mood.tracker.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zatswahm.mood.tracker.R
import com.zatswahm.mood.tracker.ui.components.AppPrimaryButton

@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onBack: () -> Boolean,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    AuthScaffold(
        title = "Welcome Back!",
        subtitle = "Sign in to access your unified women's health companion.",
        showBack = false,
        onBack = null
    ) { _ ->
        AuthTextField(
            label = "Email address",
            value = email,
            onValueChange = { email = it },
            placeholder = "example@gmail.com"
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(
            label = "Password",
            value = password,
            onValueChange = { password = it },
            isPassword = true,
            passwordVisible = passwordVisible,
            onTogglePasswordVisibility = { passwordVisible = !passwordVisible },
            placeholder = "@Sn123hsn#"
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Text(
                    text = "Remember me",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                )
            }
            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onForgotPasswordClick() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        AppPrimaryButton(
            text = "Sign in",
            onClick = { /* TODO hook login */ }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Or continue with",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))
        SocialGoogleButton(onClick = onGoogleClick)

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Don’t have an account? Sign up",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSignUpClick() },
            textAlign = TextAlign.Center
        )
    }
}


