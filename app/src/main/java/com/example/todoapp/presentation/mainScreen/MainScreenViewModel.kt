package com.example.todoapp.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.TaskModel
import com.example.todoapp.data.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) :
    ViewModel() {

    private val _state = MutableStateFlow(TaskScreenState())
    val state: StateFlow<TaskScreenState> = _state

    init {
        getCategories()
            getTasks()
    }

    fun createTask(taskModel: TaskModel) {
        viewModelScope.launch {
            try {
                taskRepository.createTask(taskModel)

                _state.update {
                    _state.value.copy(
                        message = "Se ha creado una nueva tarea"
                    )
                }
                getTasks()
            } catch (e: Exception) {
                _state.update {
                    _state.value.copy(
                        message = e.message
                    )
                }

                println("TaskError: ${e.message}")
            }
        }
    }

    private fun getTasks(){
        viewModelScope.launch {
            try {
                val response = taskRepository.getTasks()

                _state.update {
                    _state.value.copy(
                        tasks = response,
                        message = ""
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    _state.value.copy(
                        message = e.message
                    )
                }

                println("TasksError: ${e.message}")
            }
        }
    }

    fun createCategory(categoryModel: CategoryModel) {
        viewModelScope.launch {
            try {
                taskRepository.createCategory(categoryModel)

                _state.update {
                    _state.value.copy(
                        message = "Se ha creado una nueva categoria"
                    )
                }
                getTasks()
            } catch (e: Exception) {
                _state.update {
                    _state.value.copy(
                        message = e.message
                    )
                }
                println("CategoryError: ${e.message}")
            }
        }
    }

    private fun getCategories(){
        viewModelScope.launch {
            try {
                val response = taskRepository.getCategories()

                _state.update {
                    _state.value.copy(
                        categories = response,
                        message = ""
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    _state.value.copy(
                        message = e.message
                    )
                }

                println("CategoriesError: ${e.message}")
            }
        }
    }

}