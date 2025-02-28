package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.todoapp.presentation.mainScreen.MainScreenViewModel
import com.example.todoapp.presentation.mainScreen.MainTaskScreen
import com.example.todoapp.ui.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {

            val mainScreenViewModel: MainScreenViewModel by viewModels()

            TodoAppTheme {
                MainTaskScreen(mainScreenViewModel)
            }
        }
    }
}

