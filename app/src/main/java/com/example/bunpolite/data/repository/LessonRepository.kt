package com.example.bunpolite.data.repository

import com.example.bunpolite.data.datalocal.dao.MainLessonDao
import com.example.bunpolite.data.dataremote.LessonRemoteDataSource
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val lessonRemoteDataSource: LessonRemoteDataSource,
    private val mainLessonDao: MainLessonDao
) {
}