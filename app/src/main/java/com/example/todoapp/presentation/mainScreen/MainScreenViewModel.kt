package com.example.todoapp.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) :
    ViewModel() {

    fun createTask() {
        viewModelScope.launch {
            try {
            } catch (e: Exception) {

            }
        }
    }

    fun getTasks(){
        viewModelScope.launch {
            try {

            } catch (e: Exception) {

            }
        }
    }

}