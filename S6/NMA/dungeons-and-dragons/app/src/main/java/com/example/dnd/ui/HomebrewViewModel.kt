package com.example.dnd.ui

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dnd.StartScreen
import com.example.dnd.data.DnDUiState
import com.example.dnd.data.Filter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomebrewViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DnDUiState())
    val uiState: StateFlow<DnDUiState> = _uiState.asStateFlow()

    fun changeFilterState(filter: Filter) {
        _uiState.update {currentState ->
            currentState.copy(
                filterName = filter.name,
                filterLevelMin = filter.levelMin,
                filterLevelMax = filter.levelMax,
                filterSchool = filter.school
            )
        }
    }

    fun getFilterName(): String {
        return uiState.value.filterName
    }

    fun getFilterSchool(): String {
        return uiState.value.filterSchool
    }

    fun getFilterLevelMin(): String {
        return uiState.value.filterLevelMin
    }

    fun getFilterLevelMax(): String {
        return uiState.value.filterLevelMax
    }

    fun setUsername(username: String) {
        _uiState.update {currentState ->
            currentState.copy(
                username = username
            )
        }
    }

    fun getUsername(): String {
        return uiState.value.username
    }

    fun getProfilePicture(): String? {
        return uiState.value.uri
    }

    fun setProfilePicture(uri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(
                uri = uri.path
            )
        }
    }

    fun getIsCameraPermitted(): Boolean {
        return uiState.value.cameraIsPermitted
    }

    fun setIsCameraPermitted(cameraIsPermitted: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                cameraIsPermitted = cameraIsPermitted
            )
        }
    }
}