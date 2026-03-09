package com.example.bunpolite.data.datalocal.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.bunpolite.data.datalocal.entities.MainLessonEntity

@Dao
interface MainLessonDao {

    @Query("SELECT * FROM main_lessons")
    fun getAllLesson(): List<MainLessonEntity>

    @Query("SELECT * FROM main_lessons WHERE id = :id")
    fun getLessonById(id: String): MainLessonEntity

}