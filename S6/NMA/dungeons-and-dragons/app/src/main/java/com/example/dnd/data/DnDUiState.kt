package com.example.dnd.data

import com.example.dnd.StartScreen

data class DnDUiState (
    val filterName: String = "",
    val filterLevelMin: String = "0",
    val filterLevelMax: String = "9",
    val filterSchool: String = "",
    val dark: Boolean = false,
    val currentPage: StartScreen = StartScreen.Home,
    val username: String = "Offline",
    val uri: String? = null,
    val cameraIsPermitted: Boolean = false
)