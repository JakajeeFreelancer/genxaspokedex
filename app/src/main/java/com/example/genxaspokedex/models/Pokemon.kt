package com.example.genxaspokedex.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Pokemon (
    val name: String,
    val mode: String
)