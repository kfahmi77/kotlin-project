package com.khoirulfahmi.kotlinportfolio

import ModernVotingScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.khoirulfahmi.kotlinportfolio.ui.theme.KotlinPortfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinPortfolioTheme {
                ModernVotingScreen()
            }
        }
    }
}

