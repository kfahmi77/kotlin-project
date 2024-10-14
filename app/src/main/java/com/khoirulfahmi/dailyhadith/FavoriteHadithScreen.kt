package com.khoirulfahmi.dailyhadith

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteHadithScreen(
    viewModel: HadithViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (FavoriteHadith) -> Unit
) {
    val favoriteHadiths by viewModel.favoriteHadiths.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hadits Favorit") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        AnimatedVisibility(
            visible = favoriteHadiths.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Belum ada hadis favorit yang disimpan.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = favoriteHadiths.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(favoriteHadiths) { favoriteHadith ->
                    FavoriteHadithItem(
                        favoriteHadith = favoriteHadith,
                        onItemClick = { onNavigateToDetail(favoriteHadith) },
                        onRemoveFavorite = { viewModel.removeFavorite(favoriteHadith) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteHadithItem(
    favoriteHadith: FavoriteHadith,
    onItemClick: () -> Unit,
    onRemoveFavorite: () -> Unit
) {
    var isFavorite by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onItemClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "HR. ${favoriteHadith.hadisName.replaceFirstChar { it.uppercase() }} No. ${favoriteHadith.number}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(
                    onClick = {
                        isFavorite = !isFavorite
                        if (!isFavorite) {
                            onRemoveFavorite()
                        }
                    }
                ) {
                    Icon(
                      Icons.Filled.Favorite ,
                        contentDescription = "Hapus dari Favorit",
                        tint =  MaterialTheme.colorScheme.primary,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = favoriteHadith.translation,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = onItemClick,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Baca Lengkap")
            }
        }
    }
}