package com.zatswahm.mood.tracker.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zatswahm.mood.tracker.R
import com.zatswahm.mood.tracker.ui.components.AppPrimaryButton

@Composable
fun SignupScreen(
    onBack: () -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit,
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    AuthScaffold(
        title = "Create Your Account",
        subtitle = "Create your account to explore period, pregnancy and medicine insights.",
        showBack = true,
        onBack = onBack
    ) { _ ->
        AuthTextField(
            label = "Full name",
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = "Alex Smith"
        )
        Spacer(modifier = Modifier.height(16.dp))
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

        Spacer(modifier = Modifier.height(24.dp))
        AppPrimaryButton(
            text = "Register",
            onClick = { /* TODO hook signup */ }
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
            text = "Already have an account? Sign in",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLoginClick() },
            textAlign = TextAlign.Center
        )
    }
}


