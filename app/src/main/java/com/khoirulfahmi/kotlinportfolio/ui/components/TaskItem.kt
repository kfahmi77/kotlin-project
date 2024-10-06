import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import com.khoirulfahmi.kotlinportfolio.models.Task

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
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
                modifier = Modifier.weight(1f),
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
            IconButton(onClick = { onEditTask(task) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = { onDeleteTask(task) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}