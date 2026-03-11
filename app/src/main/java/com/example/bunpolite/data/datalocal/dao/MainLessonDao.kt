package com.example.bunpolite.data.datalocal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bunpolite.data.datalocal.entities.MainLessonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MainLessonDao {

    @Query("SELECT * FROM Main_lesson ORDER BY idx ASC")
    fun getAllLessons(): Flow<List<MainLessonEntity>>

    @Query("SELECT * FROM Main_lesson WHERE id = :id")
    suspend fun getLessonById(id: String): MainLessonEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLessons(lessons: List<MainLessonEntity>)

    @Query("DELETE FROM Main_lesson")
    suspend fun clearAllLessons()

}