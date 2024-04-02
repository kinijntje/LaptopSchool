package com.example.dnd.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dnd.data.DnDUiState
import com.example.dnd.ui.HomebrewViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private val DarkColorPalette = darkColors(
    primary = Dark2,
    primaryVariant = Dark4,
    secondary = Dark3,
    background = Dark1,
    surface = Dark6,
    onSurface = Dark5
)

private val LightColorPalette = lightColors(
    primary = Light5,
    primaryVariant = Light1,
    secondary = Light3,
    background = Light4,
    surface = Light6,
    onSurface = Light2
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DnDTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}