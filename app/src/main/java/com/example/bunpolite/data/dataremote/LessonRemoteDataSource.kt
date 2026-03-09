package com.example.bunpolite.data.dataremote

import com.example.bunpolite.data.dataremote.externalmodel.MainLessonNetwork
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LessonRemoteDataSource @Inject constructor(firestore: FirebaseFirestore) {

    private val lessonCollectionRef = firestore.collection(LESSON_COLLECTION)

    suspend fun getLessonList(): List<MainLessonNetwork> {
        return lessonCollectionRef.get().await().toObjects()
    }

    companion object {
        private const val LESSON_COLLECTION = "lessons"
        private const val EXERCISE_COLLECTION = "exercises"
        private const val REFERENCE_COLLECTION = "reference"
        private const val ANSWER_COLLECTION = "answers"
        private const val INDEX_FIELD = "index"
    }
}