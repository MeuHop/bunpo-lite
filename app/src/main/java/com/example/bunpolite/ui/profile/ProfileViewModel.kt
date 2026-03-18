package com.example.bunpolite.ui.profile

import androidx.lifecycle.ViewModel
import com.example.bunpolite.data.dataremote.AuthRemoteDataSource
import com.example.bunpolite.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
}