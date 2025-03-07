package com.example.todoapp.presentation.mainScreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.todoapp.FakeRepository
import com.example.todoapp.ui.theme.TodoAppTheme
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainTaskScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun hello_fun() {
        val viewModel = MainScreenViewModel(FakeRepository())

        composeTestRule.setContent {
            TodoAppTheme {
                MainTaskScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithText("Search...")
    }
}

