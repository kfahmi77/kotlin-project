package com.khoirulfahmi.kotlinportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
