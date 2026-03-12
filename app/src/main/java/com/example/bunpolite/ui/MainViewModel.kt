package com.example.bunpolite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunpolite.data.model.ThemeType
import com.example.bunpolite.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsRepository: SettingsRepository
) : ViewModel() {

    val themeFlow = settingsRepository.themeFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, ThemeType.System)
}