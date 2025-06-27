package com.github.umangtapania.mytasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.github.umangtapania.mytasks.ui.theme.MyTasksTheme
import com.github.umangtapania.mytasks.view.MyTasksApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyTasksApp()
                }
            }
        }
    }
}

@Composable
fun Modifier.dismissKeyboardOnTap(): Modifier {
    val focusManager = LocalFocusManager.current
    return pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
        })
    }
}