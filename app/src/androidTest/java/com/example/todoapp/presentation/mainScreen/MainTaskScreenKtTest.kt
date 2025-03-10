package com.example.todoapp.presentation.mainScreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.todoapp.FakeRepository
import com.example.todoapp.ui.theme.TodoAppTheme
import org.junit.Rule
import org.junit.Test

class MainTaskScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    val viewModel = MainScreenViewModel(FakeRepository())

    @Test
    fun screenChargeCorrectly() {
        composeTestRule.setContent {
            TodoAppTheme {
                MainTaskScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithTag("tfSearch")
            .assertExists("El campo de búsqueda no está visible")
        composeTestRule.onNodeWithTag("btnAdd")
            .assertExists("El botón de agregar no está visible")
    }

    @Test
    fun openUpCreateScreenAndCreateTask() {
        composeTestRule.setContent {
            TodoAppTheme {
                MainTaskScreen(viewModel)
            }
        }

        composeTestRule.onNodeWithTag("btnAdd").performClick()
        composeTestRule.onNodeWithTag("screenCreateTask")
            .assertExists("El campo de creación no está visible")
        composeTestRule.onNodeWithTag("tfNameTask").performTextInput("Tarea")
        composeTestRule.onNodeWithTag("btnMenuIcon").performClick()
        composeTestRule.onNodeWithTag("category1").performClick()
        composeTestRule.onNodeWithTag("btnCreateTask").performClick()
    }

    @Test
    fun openUpUpdateScreenAndUpdateATask() {
        composeTestRule.setContent {
            TodoAppTheme {
                MainTaskScreen(viewModel)
            }
        }

        composeTestRule.onNodeWithTag("btnTaskOptions1").performClick()
        composeTestRule.onNodeWithTag("btnEditMenu").performClick()
        composeTestRule.onNodeWithTag("screenUpdateTask")
            .assertExists("La pantalla para editar tareas no se muestra")
        composeTestRule.onNodeWithTag("tfTaskName")
            .performTextInput("Comprar 3 litros de leche condensada")
        composeTestRule.onNodeWithTag("btnUpdateTask").performClick()
    }

    @Test
    fun openUpDeleteScreenAndDeleteATask() {
        composeTestRule.setContent {
            TodoAppTheme {
                MainTaskScreen(viewModel)
            }
        }

        composeTestRule.onNodeWithTag("btnTaskOptions1").performClick()
        composeTestRule.onNodeWithTag("btnDeleteMenu").performClick()
        composeTestRule.onNodeWithTag("screenDeleteTask")
            .assertExists("La pantalla para eliminar tareas no se muestra")
        composeTestRule.onNodeWithTag("btnCancel").performClick()

        composeTestRule.onNodeWithTag("btnTaskOptions1").performClick()
        composeTestRule.onNodeWithTag("btnDeleteMenu").performClick()
        composeTestRule.onNodeWithTag("screenDeleteTask")
            .assertExists("La pantalla para eliminar tareas no se muestra")
        composeTestRule.onNodeWithTag("btnDelete").performClick()

    }

    @Test
    fun doSearchAndDeleteTask() {
        composeTestRule.setContent {
            TodoAppTheme {
                MainTaskScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithTag("tfSearch").performTextInput("queso")
        composeTestRule.onNodeWithTag("btnSearch").performClick()
    }

    @Test
    fun navigateToCategoriesScreen() {
        composeTestRule.setContent {
            TodoAppTheme {
                MainTaskScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithTag("btnMenuDrawer").performClick()
        composeTestRule.onNodeWithTag("CustomDrawerMenu")
            .assertExists("No apareció el custom drawer")
        composeTestRule.onNodeWithTag("btnMenuCategory").performClick()
        composeTestRule.onNodeWithTag("categoryScreen")
            .assertExists("No cargó la pantalla de categorias")
    }

    @Test
    fun navigateToCategoriesScreenAndCreateCategory() {
        composeTestRule.setContent {
            TodoAppTheme {
                MainTaskScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithTag("btnMenuDrawer").performClick()
        composeTestRule.onNodeWithTag("CustomDrawerMenu")
            .assertExists("No apareció el custom drawer")
        composeTestRule.onNodeWithTag("btnMenuCategory").performClick()
        composeTestRule.onNodeWithTag("categoryScreen")
            .assertExists("No cargó la pantalla de categorias")
        composeTestRule.onNodeWithTag("btnMenuDrawer").performClick()
        composeTestRule.onNodeWithTag("btnCreate").performClick()
        composeTestRule.onNodeWithTag("createCategoryDialog")
            .assertExists("No cargó la pantalla para crear categorías")
        composeTestRule.onNodeWithTag("tfCategory").performTextInput("Hobby")
        composeTestRule.onNodeWithTag("btnCreateTask").performClick()
    }

    @Test
    fun navigateToCategoriesScreenAndDeleteCategory() {
        composeTestRule.setContent {
            TodoAppTheme {
                MainTaskScreen(viewModel)
            }
        }

        composeTestRule.onNodeWithTag("btnMenuDrawer").performClick()
        composeTestRule.onNodeWithTag("CustomDrawerMenu")
            .assertExists("No apareció el custom drawer")
        composeTestRule.onNodeWithTag("btnMenuCategory").performClick()
        composeTestRule.onNodeWithTag("categoryScreen")
            .assertExists("No cargó la pantalla de categorias")
        composeTestRule.onNodeWithTag("btnMenuDrawer").performClick()
        composeTestRule.onNodeWithTag("btnOptions1").performClick()
        composeTestRule.onNodeWithTag("btnMenuDelete1").performClick()
        composeTestRule.onNodeWithTag("screenDeleteTask")
            .assertExists("No se muestra la pantalla para eliminar la tarea")
        composeTestRule.onNodeWithTag("btnDelete").performClick()
        composeTestRule.onNodeWithTag("screenDeleteTask")
            .assertDoesNotExist()
    }

    @Test
    fun navigateToCategoriesScreenAndEditCategory() {
        composeTestRule.setContent {
            TodoAppTheme {
                MainTaskScreen(viewModel)
            }
        }

        composeTestRule.onNodeWithTag("btnMenuDrawer").performClick()
        composeTestRule.onNodeWithTag("CustomDrawerMenu")
            .assertExists("No apareció el custom drawer")
        composeTestRule.onNodeWithTag("btnMenuCategory").performClick()
        composeTestRule.onNodeWithTag("categoryScreen")
            .assertExists("No cargó la pantalla de categorias")
        composeTestRule.onNodeWithTag("btnMenuDrawer").performClick()
        composeTestRule.onNodeWithTag("btnOptions1").performClick()
        composeTestRule.onNodeWithTag("btnMenuEdit1").performClick()
        composeTestRule.onNodeWithTag("editCategoryScreen")
            .assertExists("No se muestra la pantalla para editar la tarea")
        composeTestRule.onNodeWithTag("tfEditCategory").performTextInput("Plata")
        composeTestRule.onNodeWithTag("btnEdit").performClick()
        composeTestRule.onNodeWithTag("editCategoryScreen")
            .assertDoesNotExist()
    }
}

