package com.example.bunpolite.data.dataremote.externalmodel

import com.google.firebase.firestore.DocumentId

data class TheoryNetwork(
    @DocumentId val id: String,
    val index: Int,
    val content: String
)
