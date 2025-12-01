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
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    onLoginClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }

    AuthScaffold(
        title = "Forgot Password?",
        subtitle = "Enter your email and we'll send a reset code instantly.",
        showBack = true,
        onBack = onBack
    ) { _ ->
        AuthTextField(
            label = "Email address",
            value = email,
            onValueChange = { email = it },
            placeholder = "example@gmail.com"
        )

        Spacer(modifier = Modifier.height(24.dp))
        AppPrimaryButton(
            text = "Send Code",
            onClick = { /* TODO hook send code */ }
        )

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


