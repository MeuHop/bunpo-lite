package com.example.bunpolite.data.datalocal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_lessons")
data class MainLessonEntity(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "idx") val index: Int = 0,
    val title: String = ""
)
