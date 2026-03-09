package com.example.bunpolite.data.datalocal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bunpolite.data.datalocal.dao.MainLessonDao
import com.example.bunpolite.data.datalocal.entities.MainLessonEntity

@Database(
    entities = [
        MainLessonEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LiteLocalDatabase : RoomDatabase() {
    abstract fun mainLessonDao(): MainLessonDao
}