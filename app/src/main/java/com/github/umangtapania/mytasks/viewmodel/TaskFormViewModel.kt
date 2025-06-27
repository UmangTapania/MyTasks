package com.github.umangtapania.mytasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.umangtapania.mytasks.model.Task
import com.github.umangtapania.mytasks.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task

    fun loadTask(id: Int) {
        viewModelScope.launch {
            _task.value = repository.getTaskById(id)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
