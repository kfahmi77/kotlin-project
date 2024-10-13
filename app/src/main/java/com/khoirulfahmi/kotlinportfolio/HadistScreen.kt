package com.khoirulfahmi.kotlinportfolio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HadithScreen(viewModel: HadithViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val backgroundColor = Color(0xFFF8F3E6) // Warna krem lembut untuk latar belakang
    val primaryColor = Color(0xFF4CAF50) // Warna hijau untuk aksen

    LaunchedEffect(Unit) {
        viewModel.loadHadith()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (val state = uiState) {
                    is HadithUiState.Loading -> CircularProgressIndicator(
                        color = primaryColor,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    is HadithUiState.Success -> HadithCard(
                        arabicText = state.content.arab,
                        translation = state.content.id,
                        narrator = "HR. Muslim No. ${state.content.number}",
                        primaryColor = primaryColor
                    )
                    is HadithUiState.Error -> Text(
                        text = state.message,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HadithCard(
    arabicText: String,
    translation: String,
    narrator: String,
    primaryColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Arabic Text
            Text(
                text = arabicText,
                modifier = Modifier.padding(bottom = 16.dp),
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = primaryColor
            )

            // Translation
            Text(
                text = translation,
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            // Narrator
            Text(
                text = narrator,
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 14.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                color = Color.Gray
            )

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { /* TODO: Implement share functionality */ },
                    modifier = Modifier.semantics { contentDescription = "Bagikan hadits" }
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null, tint = primaryColor)
                }
                IconButton(
                    onClick = { /* TODO: Implement bookmark functionality */ },
                    modifier = Modifier.semantics { contentDescription = "Simpan hadits" }
                ) {
                    Icon(Icons.Filled.Info, contentDescription = null, tint = primaryColor)
                }
            }
        }
    }
}