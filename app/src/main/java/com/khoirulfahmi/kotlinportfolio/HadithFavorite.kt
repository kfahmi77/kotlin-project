package com.khoirulfahmi.kotlinportfolio

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_hadiths")
data class FavoriteHadith(
    @PrimaryKey val id: String,
    val hadisName: String,
    val number: Int,
    val arab: String,
    val translation: String
)