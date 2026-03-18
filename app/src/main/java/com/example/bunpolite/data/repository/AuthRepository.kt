package com.example.bunpolite.data.repository

import com.example.bunpolite.data.dataremote.AuthRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    val isSignedIn = authRemoteDataSource.getAuthState()
    suspend fun signIn(email: String, password: String) {
        authRemoteDataSource.signIn(email, password)
    }

    suspend fun signUp(email: String, password: String) {
        authRemoteDataSource.signUp(email, password)
    }

    fun signOut() {
        authRemoteDataSource.signOut()
    }
}