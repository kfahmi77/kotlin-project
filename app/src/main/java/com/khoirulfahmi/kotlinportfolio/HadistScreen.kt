package com.khoirulfahmi.kotlinportfolio

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun HadithScreen(viewModel: HadithViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val backgroundColor = Color(0xFFF8F3E6) // Warna krem lembut untuk latar belakang
    val primaryColor = Color(0xFF4CAF50) // Warna hijau untuk aksen

    LaunchedEffect(Unit) {
        viewModel.loadRandomHadith()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = uiState) {
                is HadithUiState.Loading -> CircularProgressIndicator(
                    color = primaryColor,
                    modifier = Modifier.align(Alignment.Center)
                )
                is HadithUiState.Success -> SwipeableHadithCard(
                    arabicText = state.content.arab,
                    translation = state.content.id,
                    narrator = "HR. ${state.hadisName.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }} No. ${state.content.number}",
                    primaryColor = primaryColor,

                    onChangeCollection = {
                        coroutineScope.launch {
                            Log.d("HadithScreen", "Changing collection hadis ${state.hadisName} no ${state.content.number}")
                            viewModel.changeHadisCollection()
                        }
                    }
                )
                is HadithUiState.Error -> Text(
                    text = state.message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun SwipeableHadithCard(
    arabicText: String,
    translation: String,
    narrator: String,
    primaryColor: Color,
    onChangeCollection: () -> Unit
) {
    rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Arabic Text
                Text(
                    text = arabicText,
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = primaryColor
                )

                // Translation
                Text(
                    text = translation,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                // Narrator
                Text(
                    text = narrator,
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
                        onClick = { onChangeCollection() },
                        modifier = Modifier.semantics { contentDescription = "Ganti koleksi hadits" }
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = null, tint = primaryColor)
                    }
                    IconButton(
                        onClick = { /* TODO: Implement bookmark functionality */ },
                        modifier = Modifier.semantics { contentDescription = "Simpan hadits" }
                    ) {
                        Icon(Icons.Filled.Favorite, contentDescription = null, tint = primaryColor)
                    }
                }
            }
        }
    }
}