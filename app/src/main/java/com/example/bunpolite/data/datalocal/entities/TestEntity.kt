package com.example.bunpolite.data.datalocal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Test")
data class TestEntity(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "idx") val index: Int = 0,
    val title: String = "",
    @ColumnInfo(name = "from_lesson") val fromLesson: Int,
    @ColumnInfo(name = "to_lesson") val toLesson: Int
)
