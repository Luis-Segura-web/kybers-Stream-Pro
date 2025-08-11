package com.kybers.streampro.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
// import com.kybers.streampro.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login to Xtream Service", // stringResource(R.string.login_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = uiState.host,
            onValueChange = viewModel::updateHost,
            label = { Text("Server Host") }, // stringResource(R.string.host_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.port,
            onValueChange = viewModel::updatePort,
            label = { Text("Port") }, // stringResource(R.string.port_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = uiState.username,
            onValueChange = viewModel::updateUsername,
            label = { Text("Username") }, // stringResource(R.string.username_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            label = { Text("Password") }, // stringResource(R.string.password_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        if (uiState.error != null) {
            Text(
                text = uiState.error ?: "Unknown error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = { viewModel.login(onLoginSuccess) },
            enabled = !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Login") // stringResource(R.string.login_button))
            }
        }
    }
}