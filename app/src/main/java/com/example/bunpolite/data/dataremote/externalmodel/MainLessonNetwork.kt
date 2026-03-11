package com.example.bunpolite.data.dataremote.externalmodel

import com.google.firebase.firestore.DocumentId

data class MainLessonNetwork(
    @DocumentId val id: String,
    val index: Int,
    val title: String,
    val categoryEn: String,
    val categoryJp: String,
    val sentencePatternEn: String,
    val sentencePatternJp: String,
)