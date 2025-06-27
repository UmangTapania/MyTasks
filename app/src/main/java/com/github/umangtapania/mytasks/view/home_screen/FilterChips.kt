package com.github.umangtapania.mytasks.view.home_screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.umangtapania.mytasks.R
import com.github.umangtapania.mytasks.model.TaskFilter

@Composable
fun FilterChips(
    selectedFilter: TaskFilter,
    onFilterSelected: (TaskFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(
            stringResource(R.string.all) to TaskFilter.ALL,
            stringResource(R.string.completed) to TaskFilter.COMPLETED,
            stringResource(R.string.pending) to TaskFilter.PENDING,
            stringResource(R.string.high_priority) to TaskFilter.HIGH_PRIORITY
        ).forEach { (label, filter) ->
            FilterChip(
                selected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) },
                label = { Text(label) }
            )
        }
    }
}