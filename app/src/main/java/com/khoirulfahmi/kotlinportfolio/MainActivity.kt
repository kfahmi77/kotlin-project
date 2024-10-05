package com.khoirulfahmi.kotlinportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khoirulfahmi.kotlinportfolio.ui.theme.KotlinPortfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinPortfolioTheme {
                TodoListScreen()
            }
        }
    }
}

data class Task(val id: Int, var text: String, var isCompleted: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen() {
    var tasks by remember { mutableStateOf(listOf<Task>()) }
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
                        tasks = tasks + Task(newId, newTask)
                        newTask = ""
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task", tint = Color.Black)
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
                            tasks = tasks.map { if (it.id == updatedTask.id) updatedTask else it }
                        },
                        onEditTask = { editingTask = it },
                        onDeleteTask = { taskToDelete ->
                            tasks = tasks.filter { it.id != taskToDelete.id }
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
                tasks = tasks.map { if (it.id == updatedTask.id) updatedTask else it }
                editingTask = null
            }
        )
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggleComplete: (Task) -> Unit,
    onEditTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { isChecked ->
                    onToggleComplete(task.copy(isCompleted = isChecked))
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = task.text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
            IconButton(onClick = { onEditTask(task) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = { onDeleteTask(task) }) {
                Icon(Icons.Default.Check, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun EditTaskDialog(
    task: Task,
    onDismissRequest: () -> Unit,
    onConfirm: (Task) -> Unit
) {
    var editedText by remember { mutableStateOf(task.text) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Edit Task") },
        text = {
            OutlinedTextField(
                value = editedText,
                onValueChange = { editedText = it },
                label = { Text("Task") }
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (editedText.isNotBlank()) {
                        onConfirm(task.copy(text = editedText))
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TodoListPreview() {
    KotlinPortfolioTheme {
        TodoListScreen()
    }
}