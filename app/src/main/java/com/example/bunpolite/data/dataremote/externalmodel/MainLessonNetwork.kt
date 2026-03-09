package com.example.bunpolite.data.dataremote.externalmodel

import com.google.firebase.firestore.DocumentId

data class MainLessonNetwork(
    @DocumentId val id: String = "",
    val index: Int = 0,
    val title: String = ""
)