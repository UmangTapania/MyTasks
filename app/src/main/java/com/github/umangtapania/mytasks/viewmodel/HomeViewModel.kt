package com.github.umangtapania.mytasks.viewmodel

import androidx.lifecycle.ViewModel
import com.github.umangtapania.mytasks.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
    constructor(repository: TaskRepository) : ViewModel() {

    val tasks = repository.allTasks // Flow<List<Task>>

}