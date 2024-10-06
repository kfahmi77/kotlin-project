package com.khoirulfahmi.kotlinportfolio.ui.components

import TaskItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khoirulfahmi.kotlinportfolio.models.Task
import com.khoirulfahmi.kotlinportfolio.repository.TodoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(viewModel: TodoListViewModel) {
    val tasks by viewModel.allTasks.observeAsState(initial = emptyList())
    var newTask by remember { mutableStateOf("") }
    var editingTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Todo List") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (newTask.isNotBlank()) {
                        val newId = (tasks.maxByOrNull { it.id }?.id ?: 0) + 1
                        viewModel.insertTask(Task(newId, newTask))
                        newTask = ""
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = newTask,
                onValueChange = { newTask = it },
                label = { Text("New Task") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onToggleComplete = { updatedTask ->
                            viewModel.updateTask(updatedTask)
                        },
                        onEditTask = { editingTask = it },
                        onDeleteTask = { taskToDelete ->
                            viewModel.deleteTask(taskToDelete)
                        }
                    )
                }
            }
        }
    }

    editingTask?.let { task ->
        EditTaskDialog(
            task = task,
            onDismissRequest = { editingTask = null },
            onConfirm = { updatedTask ->
                viewModel.updateTask(updatedTask)
                editingTask = null
            }
        )
    }
}