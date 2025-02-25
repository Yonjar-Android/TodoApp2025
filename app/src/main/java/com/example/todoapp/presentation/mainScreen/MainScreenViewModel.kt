package com.example.todoapp.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskModel
import com.example.todoapp.data.models.TaskWithCategoryColor
import com.example.todoapp.data.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) :
    ViewModel() {

    val tasks: StateFlow<List<TaskWithCategoryColor>> = taskRepository.getTasksColor()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val categories: StateFlow<List<CategoryWithTaskCount>> = taskRepository.getCategoriesCountTask()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun createTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {
            try {
                taskRepository.createTask(taskModel)

                _message.value = "Se ha creado una nueva tarea"

            } catch (e: Exception) {
                _message.value = e.message

                println("TaskError: ${e.message}")
            }
        }
    }

    fun updateTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {
            try {
                taskRepository.updateTask(taskModel)
                _message.value = null

            } catch (e: Exception) {
                _message.value = e.message


                println("TaskError: ${e.message}")
            }
        }
    }

    fun deleteTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(taskModel)
                _message.value = "Se eliminado una tarea"

            } catch (e: Exception) {
                _message.value = e.message

                println("TaskError: ${e.message}")
            }
        }
    }

    fun createCategory(categoryModel: CategoryModel) {
        viewModelScope.launch {
            try {
                taskRepository.createCategory(categoryModel)

                _message.update {
                    "Se ha creado una nueva categoria"
                }
            } catch (e: Exception) {
                _message.update {
                    e.message
                }
                println("CategoryError: ${e.message}")
            }
        }
    }

    fun updateCategory(categoryModel: CategoryModel){
        loadingState()
        viewModelScope.launch {
            try {
                taskRepository.updateCategory(categoryModel)
                _message.value = null

            } catch (e: Exception) {
                _message.value = e.message


                println("TaskError: ${e.message}")
            }
        }
    }

    fun deleteCategory(categoryModel: CategoryModel){
        loadingState()
        viewModelScope.launch {
            try {
                taskRepository.deleteCategory(categoryModel)

                _message.update {
                    "Se ha eliminado la categoria"
                }
            } catch (e: Exception) {
                _message.update {
                    e.message
                }
                println("CategoryError: ${e.message}")
            }
        }
    }

    fun resetMessages() {
        _message.update {
            null
        }
        _isLoading.update {
            false
        }
    }

    // Función para activar la pantalla de carga

    private fun loadingState() {
        _isLoading.update {
            true
        }
    }

}