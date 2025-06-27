package com.github.umangtapania.mytasks.view.add_task_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.umangtapania.mytasks.MyApp
import com.github.umangtapania.mytasks.R
import com.github.umangtapania.mytasks.model.Task
import com.github.umangtapania.mytasks.model.TaskPriority
import com.github.umangtapania.mytasks.model.TaskStatus
import com.github.umangtapania.mytasks.view.common_components.DatePickerModal
import kotlin.toString

@Composable
fun TaskFormScreen(
    initialTask: Task? = null,
    onSaveClick: (Task) -> Unit,
    onDelete : (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(initialTask?.title ?: "") }
    var priority by remember { mutableStateOf(initialTask?.priority ?: TaskPriority.MEDIUM) }
    var isCompleted by remember { mutableStateOf(initialTask?.status == TaskStatus.COMPLETED) }
    var deadline by remember { mutableStateOf(initialTask?.deadline) }

    LaunchedEffect(initialTask) {
        title = initialTask?.title ?: ""
        priority = initialTask?.priority ?: TaskPriority.MEDIUM
        isCompleted = initialTask?.status == TaskStatus.COMPLETED
        deadline = initialTask?.deadline
    }

    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = { deadline = it },
            onDismiss = { showDatePicker = false }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(text = if (initialTask == null) stringResource(R.string.add_task_details) else stringResource(
            R.string.update_task_details
        ), style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Priority")

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TaskPriority.entries.forEach { option ->
                FilterChip(
                    selected = priority == option,
                    onClick = { priority = option },
                    label = {
                        Text(option.name.lowercase().replaceFirstChar { it.uppercase() })
                    }
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.completed))
            Switch(checked = isCompleted, onCheckedChange = { isCompleted = it })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.deadline, deadline ?: "-----"))
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
        }

        Spacer(modifier = Modifier.weight(1f))

        if (initialTask != null){
            Button(
                onClick = { onDelete(initialTask) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text(text = "Delete Task", color = Color.White)
            }
        }
        Button(
            onClick = {
                if (title.isNullOrBlank()){
                    Toast.makeText(MyApp.applicationContext(),
                        MyApp.applicationContext().getString(R.string.title_can_not_be_empty), Toast.LENGTH_SHORT).show()
                } else if (deadline.isNullOrBlank()){
                    Toast.makeText(MyApp.applicationContext(),
                        MyApp.applicationContext().getString(R.string.deadline_can_not_be_empty), Toast.LENGTH_SHORT).show()
                }else {
                    val task = Task(
                        id = initialTask?.id ?: 0,
                        title = title,
                        priority = priority,
                        status = if (isCompleted) TaskStatus.COMPLETED else TaskStatus.PENDING,
                        deadline = deadline.toString()
                    )
                    onSaveClick(task)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (initialTask == null) stringResource(R.string.add_task) else stringResource(R.string.update_task))
        }
    }
}
