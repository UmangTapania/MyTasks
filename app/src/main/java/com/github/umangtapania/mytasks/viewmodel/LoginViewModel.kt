package com.github.umangtapania.mytasks.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.umangtapania.mytasks.MyApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)
    var isLoggedIn by mutableStateOf(false)

    fun onLoginClick() {
        if (email.isBlank()) {
            errorMessage = "Email cannot be empty"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = "Invalid email format"
        } else if(password.isBlank()){
            errorMessage = "Password can not be empty"
        } else if (password != "1111") {
            errorMessage = "Email and Password does not match"
        } else {
            MyApp.prefs.setIsLogin()
            errorMessage = null
            isLoggedIn = true
        }
    }
}