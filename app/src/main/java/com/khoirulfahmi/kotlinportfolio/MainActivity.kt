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
import androidx.lifecycle.ViewModelProvider
import com.khoirulfahmi.kotlinportfolio.local.AppDatabase
import com.khoirulfahmi.kotlinportfolio.repository.TaskRepository
import com.khoirulfahmi.kotlinportfolio.repository.TodoListViewModel
import com.khoirulfahmi.kotlinportfolio.repository.TodoListViewModelFactory
import com.khoirulfahmi.kotlinportfolio.ui.components.TodoListScreen
import com.khoirulfahmi.kotlinportfolio.ui.theme.KotlinPortfolioTheme
class MainActivity : ComponentActivity() {
    private lateinit var todoListViewModel: TodoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = TaskRepository(database.taskDao())
        todoListViewModel = ViewModelProvider(this, TodoListViewModelFactory(repository))
            .get(TodoListViewModel::class.java)

        setContent {
            KotlinPortfolioTheme {
                TodoListScreen(todoListViewModel)
            }
        }
    }
}
