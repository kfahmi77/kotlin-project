package com.khoirulfahmi.kotlinportfolio.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.khoirulfahmi.kotlinportfolio.models.Task


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