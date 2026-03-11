package com.example.bunpolite.data.dataremote.externalmodel

import com.google.firebase.firestore.DocumentId

data class QuestionNetwork(
    @DocumentId val id: String,
    val question: String = "",
    val type: Int = 1,
    val options: List<String>? = null,
    val answers: List<String> = emptyList()
)
