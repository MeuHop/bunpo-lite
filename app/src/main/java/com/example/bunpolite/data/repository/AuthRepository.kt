package com.example.bunpolite.data.repository

import com.example.bunpolite.data.dataremote.AuthRemoteDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    suspend fun logIn(email: String, password: String) {
        authRemoteDataSource.signIn(email, password)
    }

    suspend fun signUp(email: String, password: String) {
        authRemoteDataSource.signUp(email, password)
    }

    fun signOut() {
        authRemoteDataSource.signOut()
    }
}