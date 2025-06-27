package com.github.umangtapania.mytasks.view.home_screen

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.umangtapania.mytasks.R
import com.github.umangtapania.mytasks.model.TaskFilter
import com.github.umangtapania.mytasks.model.TaskPriority
import com.github.umangtapania.mytasks.model.TaskStatus
import com.github.umangtapania.mytasks.utils.Constants
import com.github.umangtapania.mytasks.view.common_components.EmptyTaskLottieAnimation
import com.github.umangtapania.mytasks.viewmodel.HomeViewModel
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf(TaskFilter.ALL) }
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    val filteredTasks = remember(tasks, selectedFilter) {
        when (selectedFilter) {
            TaskFilter.ALL -> tasks
            TaskFilter.COMPLETED -> tasks.filter { it.status == TaskStatus.COMPLETED }
            TaskFilter.PENDING -> tasks.filter { it.status != TaskStatus.COMPLETED }
            TaskFilter.HIGH_PRIORITY -> tasks.filter {
                it.priority == TaskPriority.HIGH
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("${Constants.ADD_TASK_SCREEN}/${-1}")
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_task)
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(12.dp)
        ) {
            Text(stringResource(R.string.my_tasks), style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))
            FilterChips(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            Spacer(modifier = Modifier.height(5.dp))

            if (filteredTasks.isEmpty()){
                EmptyTaskLottieAnimation()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredTasks) { task ->
                        TaskCard(task = task){
                            navController.navigate("${Constants.ADD_TASK_SCREEN}/${it}")
                        }
                    }
                }
            }
        }
    }

    val context = LocalContext.current
    var backPressedOnce by remember { mutableStateOf(false) }

    LaunchedEffect(backPressedOnce) {
        if (backPressedOnce) {
            delay(2000)
            backPressedOnce = false
        }
    }

    BackHandler {
        if (backPressedOnce) {
            // Exit the app
            (context as? Activity)?.finish()
        } else {
            backPressedOnce = true
            Toast.makeText(context,
                context.getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT).show()
        }
    }
}