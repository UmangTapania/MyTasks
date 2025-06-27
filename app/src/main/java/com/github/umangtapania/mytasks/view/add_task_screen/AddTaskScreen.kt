package com.github.umangtapania.mytasks.view.add_task_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.umangtapania.mytasks.dismissKeyboardOnTap
import com.github.umangtapania.mytasks.viewmodel.TaskFormViewModel

@Composable
fun AddOrEditTaskScreen(
    taskId: Int,
    viewModel: TaskFormViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val task by viewModel.task.collectAsState()

    LaunchedEffect(taskId) {
        if (taskId != -1) viewModel.loadTask(taskId)
    }

    TaskFormScreen(
        initialTask = if (taskId != -1) task else null,
        onSaveClick = { task ->
            if (taskId == -1) {
                viewModel.addTask(task)
            } else {
                viewModel.updateTask(task)
            }
            navController.popBackStack()
        },
        onDelete = {
            viewModel.deleteTask(it)
            navController.popBackStack()},
        modifier = Modifier.dismissKeyboardOnTap()
    )
}


