package com.example.dnd.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class Filter (
    var levelMin: String,
    var levelMax: String,
    var name: String,
    var school: String
)