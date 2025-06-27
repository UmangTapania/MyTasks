package com.github.umangtapania.mytasks.view.login_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.umangtapania.mytasks.R
import com.github.umangtapania.mytasks.dismissKeyboardOnTap
import com.github.umangtapania.mytasks.utils.Constants
import com.github.umangtapania.mytasks.view.common_components.PasswordTextField
import com.github.umangtapania.mytasks.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), navController: NavHostController) {

    if (viewModel.isLoggedIn) {
        LaunchedEffect(Unit) {
            navController.navigate(Constants.HOME_SCREEN){
                popUpTo(Constants.LOGIN_SCREEN) {
                    inclusive = true // This removes LOGIN_SCREEN from the back stack
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .dismissKeyboardOnTap(),
        verticalArrangement = Arrangement.Center
    ) {
        LoginScreenAnim()

        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(R.string.login), style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text(stringResource(R.string.email)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField{
            viewModel.password = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onLoginClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.login))
        }

        viewModel.errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = Color.Red)
        }
    }
}