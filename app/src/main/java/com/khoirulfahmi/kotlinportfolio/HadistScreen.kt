package com.khoirulfahmi.kotlinportfolio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HadithScreen() {
    val backgroundColor = Color(0xFFF8F3E6) // Warna krem lembut untuk latar belakang
    val primaryColor = Color(0xFF4CAF50) // Warna hijau untuk aksen

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HadithCard(
                arabicText = "مَنْ سَلَكَ طَرِيقًا يَلْتَمِسُ فِيهِ عِلْمًا سَهَّلَ اللَّهُ لَهُ بِهِ طَرِيقًا إِلَى الْجَنَّةِ",
                translation = "Barangsiapa menempuh jalan untuk mencari ilmu, maka Allah akan memudahkan baginya jalan menuju surga.",
                narrator = "Diriwayatkan oleh Muslim",
                primaryColor = primaryColor
            )
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
            .padding(16.dp),
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
                IconButton(onClick = { /* TODO: Implement share functionality */ }) {
                    Icon(Icons.Filled.Share, contentDescription = "Share", tint = primaryColor)
                }
                IconButton(onClick = { /* TODO: Implement bookmark functionality */ }) {
                    Icon(Icons.Filled.Info, contentDescription = "Bookmark", tint = primaryColor)
                }
            }
        }
    }
}