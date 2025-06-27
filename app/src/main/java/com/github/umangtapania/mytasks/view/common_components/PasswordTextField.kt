package com.github.umangtapania.mytasks.view.common_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.github.umangtapania.mytasks.R

@Composable
fun PasswordTextField(
    onPasswordChange: (String) -> Unit
) {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { newPassword ->
            password = newPassword
            onPasswordChange(password)
        },
        label = { Text(stringResource(R.string.password)) },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val iconRes = if (passwordVisible) R.drawable.icon_visiblity_off else R.drawable.icon_visible
            val desc = if (passwordVisible) stringResource(R.string.hide_password) else stringResource(
                R.string.show_password
            )

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = painterResource(id = iconRes), contentDescription = desc)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}