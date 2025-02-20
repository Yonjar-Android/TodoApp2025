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
        getCategoriesWithTaskCount()
        getTasksColor()
    }

    /*private fun getTasks(){
        viewModelScope.launch {
            try {
                val response = taskRepository.getTasks()

                _state.update {
                    _state.value.copy(
                        tasks = response)
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
    }*/

    private fun getTasksColor(){
        viewModelScope.launch {
            try {
                val response = taskRepository.getTasksColor()

                _state.update {
                    _state.value.copy(
                        tasks = response)
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

    fun createTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {
            try {
                taskRepository.createTask(taskModel)

                _state.update {
                    _state.value.copy(
                        message = "Se ha creado una nueva tarea"
                    )
                }
                getTasksColor()
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

    fun updateTask(taskModel: TaskModel){
        loadingState()
        viewModelScope.launch {
            try {
                taskRepository.updateTask(taskModel)
                _state.update {
                    _state.value.copy(
                        message = null
                    )
                }
                getTasksColor()
            } catch (e: Exception){
                _state.update {
                    _state.value.copy(
                        message = e.message
                    )
                }

                println("TaskError: ${e.message}")
            }
        }
    }

    fun deleteTask(taskModel: TaskModel){
        loadingState()
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(taskModel)
                _state.update {
                    _state.value.copy(
                        message = "Se eliminado una tarea"
                    )
                }
                getTasksColor()
            } catch (e: Exception){
                _state.update {
                    _state.value.copy(
                        message = e.message
                    )
                }

                println("TaskError: ${e.message}")
            }
        }
    }


    /*private fun getCategories(){
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
    }*/

    private fun getCategoriesWithTaskCount(){
        viewModelScope.launch {
            try {
                val response = taskRepository.getCategoriesCountTask()

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

    fun createCategory(categoryModel: CategoryModel) {
        viewModelScope.launch {
            try {
                taskRepository.createCategory(categoryModel)

                _state.update {
                    _state.value.copy(
                        message = "Se ha creado una nueva categoria"
                    )
                }
                getTasksColor()
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

    fun resetMessages() {
        _state.update {
            _state.value.copy(message = null, isLoading = false)
        }
    }

    // Función para activar la pantalla de carga

    private fun loadingState() {
        _state.update {
            _state.value.copy(
                isLoading = true
            )
        }
    }

}