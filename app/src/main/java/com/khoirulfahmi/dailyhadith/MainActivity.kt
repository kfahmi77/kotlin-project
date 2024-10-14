package com.khoirulfahmi.dailyhadith

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khoirulfahmi.dailyhadith.ui.theme.KotlinPortfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: HadithViewModel = viewModel()
            KotlinPortfolioTheme {
              AppNavigation(viewModel)
            }
        }
    }
}