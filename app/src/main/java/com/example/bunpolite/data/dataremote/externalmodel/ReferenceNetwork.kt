package com.example.bunpolite.data.dataremote.externalmodel

import com.google.firebase.firestore.DocumentId

data class ReferenceNetwork(
    @DocumentId val id: String,
    val jpText: String = "",
    val meaning: String = ""
)
