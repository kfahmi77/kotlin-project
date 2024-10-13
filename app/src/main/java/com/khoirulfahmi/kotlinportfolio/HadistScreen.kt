package com.khoirulfahmi.kotlinportfolio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HadithCard(
    arabicText: String,
    translation: String,
    narrator: String,
    primaryColor: Color,
    onChangeCollection: () -> Unit,
    onShare: () -> Unit,
    onBookmark: () -> Unit
) {
    val cardShape = RoundedCornerShape(16.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(
                elevation = 8.dp,
                shape = cardShape,
                clip = true
            ),
        shape = cardShape,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Narrator
            Text(
                text = narrator,
                style = MaterialTheme.typography.labelMedium,
                color = primaryColor,
                fontWeight = FontWeight.Bold
            )

            // Arabic Text
            Text(
                text = arabicText,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Divider(color = primaryColor.copy(alpha = 0.2f), thickness = 1.dp)

            // Translation
            Text(
                text = translation,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onShare,
                    modifier = Modifier.semantics { contentDescription = "Bagikan hadits" }
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null, tint = primaryColor)
                }
                IconButton(
                    onClick = onChangeCollection,
                    modifier = Modifier.semantics { contentDescription = "Ganti koleksi hadits" }
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = null, tint = primaryColor)
                }
                IconButton(
                    onClick = onBookmark,
                    modifier = Modifier.semantics { contentDescription = "Simpan hadits" }
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = null, tint = primaryColor)
                }
            }
        }
    }
}

@Composable
fun HadithScreen(viewModel: HadithViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val backgroundColor = Color(0xFFF5F5F5) // Light gray background
    val primaryColor = Color(0xFF4CAF50) // Green for accent

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
                is HadithUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        HadithCard(
                            arabicText = state.content.arab,
                            translation = state.content.id,
                            narrator = "HR. ${state.hadisName.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase() else it.toString()
                            }} No. ${state.content.number}",
                            primaryColor = primaryColor,
                            onChangeCollection = {
                                viewModel.changeHadisCollection()
                            },
                            onShare = { /* Implement share functionality */ },
                            onBookmark = { /* Implement bookmark functionality */ }
                        )
                    }
                }
                is HadithUiState.Error -> ErrorMessage(
                    message = state.message,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

// ErrorMessage composable remains unchanged

@Composable
fun ErrorMessage(message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}