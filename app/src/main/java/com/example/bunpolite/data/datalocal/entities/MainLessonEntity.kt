package com.example.bunpolite.data.datalocal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bunpolite.data.model.MainLesson

@Entity(tableName = "Main_lesson")
data class MainLessonEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "idx") val index: Int,
    val title: String,
    @ColumnInfo(name = "category_en") val categoryEn: String,
    @ColumnInfo(name = "category_jp") val categoryJp: String,
    @ColumnInfo(name = "sentence_pattern_en") val sentencePatternEn: String,
    @ColumnInfo(name = "sentence_pattern_jp") val sentencePatternJp: String,
)

fun MainLessonEntity.asModel() = MainLesson(
    id = id,
    index = index,
    title = title,
    categoryEn = categoryEn,
    categoryJp = categoryJp,
    sentencePatternEn = sentencePatternEn,
    sentencePatternJp = sentencePatternJp
)
