package com.example.bunpolite.data.dataremote.externalmodel

import com.example.bunpolite.data.datalocal.entities.TestEntity
import com.google.firebase.firestore.DocumentId

data class TestNetwork(
    @DocumentId val id: String,
    val index: Int,
    val title: String,
    val fromLesson: Int,
    val toLesson: Int
)

fun TestNetwork.asEntity() = TestEntity(
    id = id,
    index = index,
    title = title,
    fromLesson = fromLesson,
    toLesson = toLesson
)