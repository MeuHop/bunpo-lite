package com.example.bunpolite.data.dataremote

import com.example.bunpolite.data.dataremote.externalmodel.MainLessonNetwork
import com.example.bunpolite.data.dataremote.externalmodel.ReferenceNetwork
import com.example.bunpolite.data.dataremote.externalmodel.TheoryNetwork
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LessonRemoteDataSource @Inject constructor(firestore: FirebaseFirestore) {

    private val lessonCollectionRef = firestore.collection(MAIN_COLLECTION)

    suspend fun getLessonList(): List<MainLessonNetwork> {
        return lessonCollectionRef.get().await().toObjects()
    }

    suspend fun getTheoryList(lessonId: String): List<TheoryNetwork> {
        return lessonCollectionRef.document(lessonId)
            .collection(THEORY_COLLECTION).get().await().toObjects()
    }

    suspend fun getReferenceList(lessonId: String): List<ReferenceNetwork> {
        return lessonCollectionRef.document(lessonId)
            .collection(REFERENCE_COLLECTION).get().await().toObjects()
    }

    suspend fun getQuestionList(lessonId: String, theoryId: String) {
        lessonCollectionRef.document(lessonId)
            .collection(THEORY_COLLECTION).document(theoryId)
            .collection(QUESTION_COLLECTION)
    }

    private companion object {
        const val MAIN_COLLECTION = "mains"
        const val THEORY_COLLECTION = "theories"
        const val QUESTION_COLLECTION = "questions"
        const val REFERENCE_COLLECTION = "reference"
    }
}