package com.github.umangtapania.mytasks.view.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.umangtapania.mytasks.R
import com.github.umangtapania.mytasks.model.Task
import com.github.umangtapania.mytasks.model.TaskPriority
import com.github.umangtapania.mytasks.model.TaskStatus
import java.time.format.DateTimeFormatter

@Composable
fun TaskCard(task: Task, onClick : (Int) -> Unit) {
    val priorityColor = when (task.priority) {
        TaskPriority.LOW -> Color.Gray
        TaskPriority.MEDIUM -> Color.Yellow
        TaskPriority.HIGH -> Color.Red
    }

    val statusColor = when(task.status){
        TaskStatus.PENDING -> Color.Red
        TaskStatus.COMPLETED -> Color.Green
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick(task.id) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Badge(contentColor = priorityColor,
                    containerColor = Color.LightGray
                ){
                    Text(task.priority.name.lowercase().replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Badge(contentColor = statusColor,
                    containerColor = Color.White
                ){
                    Text(text = task.status.name.lowercase().replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.deadline,task.deadline.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))),
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
    }
}
