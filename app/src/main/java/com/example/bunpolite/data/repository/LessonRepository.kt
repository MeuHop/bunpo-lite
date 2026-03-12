package com.example.bunpolite.data.repository

import com.example.bunpolite.data.datalocal.dao.MainLessonDao
import com.example.bunpolite.data.datalocal.entities.MainLessonEntity
import com.example.bunpolite.data.datalocal.entities.asModel
import com.example.bunpolite.data.dataremote.LessonRemoteDataSource
import com.example.bunpolite.data.dataremote.externalmodel.MainLessonNetwork
import com.example.bunpolite.data.dataremote.externalmodel.asEntity
import com.example.bunpolite.data.model.MainLesson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val lessonRemoteDataSource: LessonRemoteDataSource,
    private val mainLessonDao: MainLessonDao
) {
    suspend fun refreshMainLessons() {
        mainLessonDao.clearAllLessons()
        val lessons = lessonRemoteDataSource.getLessonList().map(MainLessonNetwork::asEntity)
        mainLessonDao.insertAllLessons(lessons)
    }

    fun getMainLessons(): Flow<List<MainLesson>> {
        return mainLessonDao.getAllLessons()
            .map { entities -> entities.map(MainLessonEntity::asModel) }
    }
}