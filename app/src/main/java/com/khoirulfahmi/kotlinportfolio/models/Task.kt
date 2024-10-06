package com.khoirulfahmi.kotlinportfolio.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: Int,
    var text: String,
    var isCompleted: Boolean = false
)