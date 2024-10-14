package com.khoirulfahmi.dailyhadith

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HadithDetailScreen(
    favoriteHadith: FavoriteHadith,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    fun shareHadith() {
        val shareText = """
        HR. ${favoriteHadith.hadisName.replaceFirstChar { it.uppercase() }} No. ${favoriteHadith.number}
        
        ${favoriteHadith.arab}
        
        Terjemahan:
        ${favoriteHadith.translation}
    """.trimIndent()

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Hadits") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { shareHadith() }) {
                        Icon(Icons.Filled.Share, contentDescription = "Bagikan")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "HR. ${favoriteHadith.hadisName.replaceFirstChar { it.uppercase() }} No. ${favoriteHadith.number}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Text(
                            text = favoriteHadith.arab,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.End
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Terjemahan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = favoriteHadith.translation,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}