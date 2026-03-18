package com.example.bunpolite.data.dataremote

import com.example.bunpolite.data.dataremote.externalmodel.TestNetwork
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TestRemoteDataSource @Inject constructor(firestore: FirebaseFirestore) {

    private val testCollectionRef = firestore.collection(TEST_COLLECTION)

    suspend fun getTestList(): List<TestNetwork> {
        return testCollectionRef.get().await().toObjects()
    }

    suspend fun getQuestionList(testId: String) {
        testCollectionRef.document(testId)
            .collection(QUESTION_COLLECTION)
    }

    private companion object {
        const val TEST_COLLECTION = "tests"
        const val QUESTION_COLLECTION = "questions"
    }
}